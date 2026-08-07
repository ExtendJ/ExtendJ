#!/usr/bin/env python3
"""TUI for running ExtendJ regression tests with multiple compiler versions.

Discovers the tests under rtest/tests, compiles each with every enabled
compiler, and shows the results side by side in columns. Compiler
diagnostics are captured and can be inspected in a fullscreen view.

The tab bar on the first line selects the target Java version under test.
Each version has its own compilers and its own JDK to run them with.

Keys:
    j/k, arrows   move cursor
    Ctrl-D/Ctrl-U scroll a quarter screen down/up (also in the file and
                  diagnostics views, where e edits the file being shown)
    g / G         jump to first / last row
    h/l, left/right  move between the test column and the result columns;
                  h on the test column steps out to the parent directory
    Enter         fold/unfold a directory; on a test, show its Test.java,
                  or, in a result column, the compiler diagnostics of that
                  result (fullscreen)
    Space         select/deselect a subtree for the next run; a selected
                  directory carries its tests with it, and a test deselected
                  afterwards is an exception to that ('*' fully selected,
                  '~' partly)
    f             select the tests that did not give the expected result
    u             clear the selection
    a             show every test, or only those of this version's test set
    r             run the selection, or the subtree under the cursor
    x             cancel pending compile jobs
    /             find tests by path (live), Esc clears, Enter keeps
    o             show only: all / where compilers differ / unexpected
    z / Z         fold / unfold all
    Tab, Shift-Tab   switch target Java version
    c             configure the active Java version
    q             quit

"""

import collections
import curses
import difflib
import glob
import json
import os
import queue
import re
import shlex
import shutil
import subprocess
import sys
import tempfile
import threading
import time

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
CONFIG_DIR = os.path.expanduser("~/.config/extendj")
CONFIG_FILE = os.path.join(CONFIG_DIR, "testing.json")
CACHE_DIR = os.path.expanduser("~/.cache/extendj-tui")
RUNNER_SOURCE = os.path.join(SCRIPT_DIR, "TestRunner.java")

EXPECT_FAIL_RESULTS = ("COMPILE_FAIL", "COMPILE_ERR_OUTPUT")

MAX_JOBS = max(1, (os.cpu_count() or 4) // 2)

# ExtendJ-only options:
EXTENDJ_OPTIONS = ("XprettyPrint", "XstructuredPrint", "XdumpTree", "XparseOnly")


def compiles_test(comp, test):
    """Whether a compiler can be asked to compile a test at all."""
    if comp.get("type") == "extendj":
        return True
    return not any(option.strip().lstrip("-") in EXTENDJ_OPTIONS
                   for option in test.props.get("options", "").split(","))

JAVA_HOMES = (
    "/usr/lib/jvm/*",
    "/usr/java/*",
    "/opt/java/*",
    "/Library/Java/JavaVirtualMachines/*/Contents/Home",
    "~/.sdkman/candidates/java/*",
    "~/.jdks/*",
)


def java_major(text):
    """The major Java version in a version string or an installation name."""
    match = re.search(r"(?:^|[^\d.])1\.(\d+)(?:\D|$)", text)  # 1.8.0 is Java 8
    if match:
        return int(match.group(1))
    match = re.search(r"\d+", text)
    return int(match.group()) if match else None


def java_home_version(home):
    """The major Java version of an installation."""
    try:
        with open(os.path.join(home, "release")) as fp:
            for line in fp:
                if line.startswith("JAVA_VERSION="):
                    return java_major(line.split("=", 1)[1].strip().strip('"'))
    except OSError:
        pass
    name = os.path.basename(home.rstrip(os.sep))
    if name in ("Home", "Contents", ""):  # macOS keeps the version further up
        name = os.path.basename(os.path.dirname(os.path.dirname(home.rstrip(os.sep))))
    version = java_major(name)
    if version is not None:
        return version
    try:  # last resort: ask the runtime itself
        proc = subprocess.run([os.path.join(home, "bin", "java"), "-version"],
                              capture_output=True, text=True, timeout=20)
        return java_major((proc.stderr + proc.stdout).split("\n")[0].split('"')[-2]
                          if '"' in proc.stderr + proc.stdout else "")
    except (OSError, subprocess.SubprocessError, IndexError):
        return None


def java_runtimes():
    """The installed Java runtimes, as {major version: java command}."""
    if JAVA_RUNTIMES:
        return JAVA_RUNTIMES
    homes = []
    for pattern in JAVA_HOMES:
        homes.extend(glob.glob(os.path.expanduser(pattern)))
    if os.environ.get("JAVA_HOME"):
        homes.append(os.environ["JAVA_HOME"])
    # Prefer full JDKs, and the plainest path among installations of one version.
    homes.sort(key=lambda h: (not os.access(os.path.join(h, "bin", "javac"), os.X_OK),
                              len(h), h))
    for home in homes:
        java = os.path.join(home, "bin", "java")
        if not os.access(java, os.X_OK):
            continue
        version = java_home_version(home)
        if version is not None and version not in JAVA_RUNTIMES:
            JAVA_RUNTIMES[version] = java
    return JAVA_RUNTIMES


JAVA_RUNTIMES = {}


def resolve_java(version):
    """The java command to test a target Java version with.

    An explicit setting is used as it is. Otherwise the installed runtime of
    the same Java version is used, so that a version is tested on its own JDK,
    falling back to whichever java is on the path.
    """
    java = (version.get("java") or "").strip()
    if java and java != "java":
        return java
    return java_runtimes().get(java_major(version.get("name", "")) or -1, "java")


def default_version(name):
    """A target Java version under test: the compilers to run and the JDK to run them."""
    return {
        "name": name,
        "java": "java",
        "compilers": [
            {
                "name": "javac",
                "type": "javac",
                "program": "javac",
                "flags": "",
                "enabled": True,
            },
            {
                "name": f"extendj {name}",
                "type": "extendj",
                "classpath": os.path.join(os.path.dirname(SCRIPT_DIR),
                                          f"java{name}", "extendj.jar"),
                "flags": "",
                "enabled": True,
            },
        ],
    }


def default_config():
    return {
        "versions": [default_version("8"), default_version("11")],
        "active": 0,
        "timeout": 60,
        "jobs": MAX_JOBS,
        "tests_root": os.path.join(SCRIPT_DIR, "tests"),
    }


def load_config():
    cfg = default_config()
    try:
        with open(CONFIG_FILE) as fp:
            stored = json.load(fp)
    except (OSError, ValueError):
        return cfg
    if "versions" not in stored and "compilers" in stored:
        # Configuration from before target Java versions were configurable.
        stored["versions"] = [{"name": "default",
                               "java": stored.pop("java", "java"),
                               "compilers": stored.pop("compilers")}]
    for key, value in stored.items():
        cfg[key] = value
    if not cfg["versions"]:
        cfg["versions"] = [default_version("8")]
    cfg["active"] = max(0, min(cfg.get("active", 0), len(cfg["versions"]) - 1))
    try:
        cfg["jobs"] = max(1, min(int(cfg["jobs"]), MAX_JOBS))
    except (KeyError, TypeError, ValueError):
        cfg["jobs"] = MAX_JOBS
    return cfg


def save_config(cfg):
    """Write the config; returns an error message or None on success."""
    try:
        os.makedirs(CONFIG_DIR, exist_ok=True)
        tmp = CONFIG_FILE + ".tmp"
        with open(tmp, "w") as fp:
            json.dump(cfg, fp, indent=2)
            fp.write("\n")
        os.replace(tmp, CONFIG_FILE)
        return None
    except OSError as ex:
        return f"cannot save config: {ex}"


def unescape_property(text):
    """Resolve the backslash escapes of a property key or value."""
    out = []
    i = 0
    while i < len(text):
        if text[i] != "\\":
            out.append(text[i])
            i += 1
            continue
        i += 1
        if i >= len(text):
            break
        char = text[i]
        i += 1
        if char == "u" and len(text) >= i + 4:
            try:
                out.append(chr(int(text[i:i + 4], 16)))
                i += 4
                continue
            except ValueError:
                pass
        out.append({"n": "\n", "r": "\r", "t": "\t", "f": "\f"}.get(char, char))
    return "".join(out)


def split_property(line):
    """Split a property definition into its key and value, honouring escapes."""
    key = []
    i = 0
    while i < len(line):
        if line[i] == "\\" and i + 1 < len(line):
            key.append(line[i:i + 2])
            i += 2
            continue
        if line[i] in " \t\f=:":
            break
        key.append(line[i])
        i += 1
    while i < len(line) and line[i] in " \t\f":
        i += 1
    if i < len(line) and line[i] in "=:":
        i += 1
        while i < len(line) and line[i] in " \t\f":
            i += 1
    return unescape_property("".join(key)), unescape_property(line[i:].strip())


def parse_property_lines(lines):
    """Parse property definitions the way java.util.Properties reads them.

    Values carry backslash escapes, so that a path written as
    '@TEST_DIR@\\/lib.jar' means '@TEST_DIR@/lib.jar', and a line ending in a
    backslash is continued by the next one.
    """
    props = {}
    continued = ""
    for raw in lines:
        line = raw.rstrip("\n").rstrip("\r")
        if continued:
            line = continued + line.lstrip()
            continued = ""
        elif not line.strip() or line.strip()[0] in "#!":
            continue
        if (len(line) - len(line.rstrip("\\"))) % 2:  # an odd number of backslashes
            continued = line[:-1]
            continue
        key, value = split_property(line.strip())
        if key:
            props[key] = value
    return props


def parse_properties(path):
    try:
        with open(path, errors="replace") as fp:
            return parse_property_lines(fp)
    except OSError:
        return {}


def parse_property_comments(path):
    """Test properties written as leading '// .name=value' comments in a source file.

    The regression test suite reads them from Test.java when a test has no
    Test.properties file, so the expected result of a test is often stated
    there and nowhere else.
    """
    lines = []
    try:
        with open(path, errors="replace") as fp:
            for line in fp:
                if not line.startswith("//"):
                    break
                dot = line.find(".")
                if dot in (2, 3):
                    lines.append(line[dot + 1:])
    except OSError:
        pass
    return parse_property_lines(lines)


class Test:
    __slots__ = ("dir", "rel", "sources", "expected_fail", "props", "expected_err")

    def __init__(self, directory, rel, sources, expected_fail, props, expected_err):
        self.dir = directory
        self.rel = rel
        self.sources = sources
        self.expected_fail = expected_fail
        self.props = props
        self.expected_err = expected_err  # file the compiler messages must match


def collect_sources(directory):
    """The Java sources of a test: its own, and those of the directories under it."""
    sources = []
    for dirpath, dirnames, filenames in os.walk(directory):
        dirnames.sort()
        for name in sorted(filenames):
            if name.endswith(".java"):
                sources.append(os.path.relpath(os.path.join(dirpath, name), directory))
    return sources


def discover_tests(root):
    """The tests under a root directory.

    A test is a directory holding a Test.java or a Test.properties file, as in
    the regression test suite. What is below such a directory belongs to the
    test: a package it declares, for instance, and not a test of its own.
    """
    tests = []
    for dirpath, dirnames, filenames in os.walk(root):
        dirnames.sort()
        if "Test.java" not in filenames and "Test.properties" not in filenames:
            continue
        dirnames[:] = []  # everything below the test directory is part of the test
        sources = collect_sources(dirpath)
        if not sources:
            continue
        rel = os.path.relpath(dirpath, root)
        if "Test.properties" in filenames:
            props = parse_properties(os.path.join(dirpath, "Test.properties"))
        elif "Test.java" in filenames:
            props = parse_property_comments(os.path.join(dirpath, "Test.java"))
        else:
            props = {}
        result = props.get("result", "")
        if result:
            expected_fail = result in EXPECT_FAIL_RESULTS
        else:
            expected_fail = os.path.basename(dirpath).endswith("f")
        expected_err = ""
        for name in ("extendj.err.expected", "compile.err.expected"):
            if name in filenames:
                expected_err = os.path.join(dirpath, name)
                break
        tests.append(Test(dirpath, rel, sources, expected_fail, props, expected_err))
    tests.sort(key=lambda t: t.rel)
    return tests


class Node:
    __slots__ = ("rel", "name", "children", "parent", "test", "expanded", "ntests")

    def __init__(self, rel, name, parent):
        self.rel = rel
        self.name = name
        self.parent = parent
        self.children = []
        self.test = None
        self.expanded = False
        self.ntests = 0


def build_tree(tests):
    root = Node("", "", None)
    index = {}
    for test in tests:
        current = root
        rel = ""
        for part in test.rel.split(os.sep):
            rel = part if not rel else rel + os.sep + part
            node = index.get(rel)
            if node is None:
                node = Node(rel, part, current)
                index[rel] = node
                current.children.append(node)
            current = node
        current.test = test

    def finish(node):
        node.children.sort(key=lambda c: c.name)
        node.ntests = (1 if node.test else 0) + sum(finish(c) for c in node.children)
        return node.ntests

    finish(root)
    index[""] = root  # the root node is the "all tests" item
    return root, index


class Result:
    __slots__ = ("status", "output", "cmd", "code", "elapsed", "path", "diag")

    def __init__(self):
        self.status = "pending"  # pending running pass fail timeout error canceled
        self.diag = ""  # "", "match" or "differ": the compiler messages compared
        self.output = ""
        self.cmd = ""
        self.code = None
        self.elapsed = 0.0
        self.path = ""

    @property
    def done(self):
        return self.status in ("pass", "fail", "timeout", "error")

    def matches_expected(self, test):
        if self.diag == "differ":
            return False
        if self.status == "pass":
            return not test.expected_fail
        if self.status == "fail":
            return test.expected_fail
        return False


class Run:
    """One test run: the tests it covers and the results they produced.

    There is one run per target Java version, so that switching between the
    version tabs shows what each version made of the tests, in the same tree.
    """

    def __init__(self):
        self.results = {}
        self.rels = set()  # tests that have a result, from this run or an earlier one
        self.comps = []
        self.filter = "all"  # all / diff / unexpected


class WorkerTimeout(Exception):
    """A compile job took longer than the configured timeout."""


class WorkerDead(Exception):
    """The worker JVM exited or lost protocol sync."""


class JvmWorker:
    """A TestRunner worker JVM, compiling one test at a time.

    Keeping the JVM alive across tests avoids paying JVM startup and compiler
    class loading for every single compilation. See TestRunner.java.
    """

    def __init__(self, cmd, cwd):
        self.cmd = cmd
        self.cwd = cwd
        self.proc = None
        self.lines = queue.Queue()
        self.errors = collections.deque(maxlen=20)
        self.next_id = 0

    def start(self, timeout):
        self.proc = subprocess.Popen(self.cmd, stdin=subprocess.PIPE,
                                     stdout=subprocess.PIPE, stderr=subprocess.PIPE,
                                     text=True, bufsize=1, cwd=self.cwd)
        threading.Thread(target=self.read_stdout, daemon=True).start()
        threading.Thread(target=self.read_stderr, daemon=True).start()
        deadline = time.time() + timeout
        while not self.line(deadline).startswith("READY"):
            pass  # LOADFAIL lines are reported again per job, by the worker itself

    def read_stdout(self):
        for line in self.proc.stdout:
            self.lines.put(line.rstrip("\n"))
        self.lines.put(None)

    def read_stderr(self):
        for line in self.proc.stderr:
            self.errors.append(line.rstrip("\n"))

    def line(self, deadline):
        try:
            line = self.lines.get(timeout=max(0.0, deadline - time.time()))
        except queue.Empty:
            raise WorkerTimeout()
        if line is None:
            raise WorkerDead("\n".join(self.errors) or "no output")
        return line

    def compile(self, index, args, timeout):
        """Compile with the index:th compiler version; returns (exit code, diagnostics)."""
        self.next_id += 1
        job = str(self.next_id)
        try:
            self.proc.stdin.write(f"{job} {index} {len(args)}\n")
            for arg in args:
                self.proc.stdin.write(arg + "\n")
            self.proc.stdin.flush()
        except (OSError, ValueError) as exc:
            raise WorkerDead(str(exc))
        deadline = time.time() + timeout
        output = []
        while True:
            line = self.line(deadline)
            if line.startswith("RESULT "):
                _, answer, code, count = line.split(" ", 3)
                break
            output.append(line)  # unexpected output: keep it for the diagnostics
        if answer != job:
            raise WorkerDead(f"protocol desync: answer for job {answer}, expected {job}")
        output.extend(self.line(deadline) for _ in range(int(count)))
        return int(code), "\n".join(output)

    def stop(self):
        if self.proc is None:
            return
        try:
            self.proc.kill()
            self.proc.wait(timeout=5)
        except (OSError, subprocess.SubprocessError):
            pass
        for stream in (self.proc.stdin, self.proc.stdout, self.proc.stderr):
            try:
                stream.close()
            except OSError:
                pass


def jvm_spec(comp):
    """The (type, classpath) worker spec for a compiler, or None to use a separate process."""
    if comp.get("type") == "javac":
        # Only the JDK's own javac can run inside the worker JVM.
        return ("javac", "") if (comp.get("program") or "javac") == "javac" else None
    return ("extendj", comp.get("classpath", ""))


def javac_for(java):
    """The javac executable matching a java executable."""
    directory, name = os.path.split(java)
    if directory and name.startswith("java"):
        return os.path.join(directory, "javac")
    return "javac"


def build_runner(java):
    """Compile TestRunner.java if needed; returns (classes directory, error message)."""
    fallback = " - compiling tests in separate JVMs"
    if not os.path.exists(RUNNER_SOURCE):
        return None, f"{RUNNER_SOURCE} not found{fallback}"
    javac = javac_for(java)
    classes = None
    for candidate in (CACHE_DIR, os.path.join(tempfile.gettempdir(), "extendj-tui-classes")):
        candidate = os.path.join(candidate, sanitize(javac))
        try:
            os.makedirs(candidate, exist_ok=True)
            classes = candidate
            break
        except OSError:
            continue
    if classes is None:
        return None, f"no writable cache directory{fallback}"
    target = os.path.join(classes, "TestRunner.class")
    if (os.path.exists(target)
            and os.path.getmtime(target) >= os.path.getmtime(RUNNER_SOURCE)):
        return classes, ""
    try:
        proc = subprocess.run([javac, "-d", classes, RUNNER_SOURCE],
                              capture_output=True, text=True, timeout=180)
    except (OSError, subprocess.SubprocessError) as exc:
        return None, f"cannot run {javac} ({exc}){fallback}"
    if proc.returncode != 0:
        return None, f"cannot compile TestRunner.java{fallback}"
    return classes, ""


CELL_TEXT = {
    "pending": "  ·",
    "running": "  …",
    "pass": "PASS",
    "fail": "FAIL",
    "timeout": "TIME",
    "error": "ERR!",
    "canceled": "CANC",
}


def error_lines(text, rel):
    """A compiler's messages about a test's own sources, without the path to them.

    Output that does not name a source file of the test, such as warnings
    about the JDK the compiler runs on, is left out. The order is dropped
    because the regression test suite compares these messages as a set.
    """
    key = rel.replace(os.sep, "/") + "/"
    lines = []
    for line in text.replace("\\", "/").splitlines():
        index = line.find(key)
        if index >= 0:
            lines.append(line[index + len(key):].rstrip())
    return sorted(lines)


def compare_diagnostics(test, comp, output):
    """Compare a compiler's messages with the error output the test expects.

    Only ExtendJ is compared: the expected files record its wording, down to
    the column numbers, so javac could never match them.
    """
    if comp.get("type") != "extendj" or not test.expected_err:
        return "", [], []
    expected = error_lines("\n".join(read_source(test.expected_err)), test.rel)
    actual = error_lines(output, test.rel)
    return ("match" if expected == actual else "differ"), expected, actual


def read_source(path):
    """The lines of a source file."""
    try:
        with open(path, errors="replace") as fp:
            return fp.read().splitlines()
    except OSError as exc:
        return [str(exc)]


def parse_test_sets(path):
    """The named test sets declared in Tests.java, as {name: [test paths]}."""
    sets = {}
    try:
        with open(path, errors="replace") as fp:
            text = fp.read()
    except OSError:
        return sets
    text = re.sub(r"//[^\n]*", "", text)  # the sets are heavily commented
    for match in re.finditer(r"String\[\]\s+(\w+)\s*=\s*\{(.*?)\}", text, re.S):
        sets[match.group(1)] = re.findall(r'"([^"]*)"', match.group(2))
    return sets


def parse_suite(path, sets):
    """The include and exclude paths a JUnit test suite is built from."""
    includes, excludes = [], []
    try:
        with open(path, errors="replace") as fp:
            text = fp.read()
    except OSError:
        return includes, excludes
    text = re.sub(r"//[^\n]*", "", text)
    for call, args in re.findall(r"properties\.(include|exclude)\(([^;]*)\)\s*;", text, re.S):
        paths = includes if call == "include" else excludes
        for name in re.findall(r"Tests\.(\w+)", args):
            paths.extend(sets.get(name, []))
        paths.extend(re.findall(r'"([^"]*)"', args))
    return includes, excludes


def suite_excludes(name):
    """The test paths left out of the suite for a target Java version.

    Returns (suite file, excluded paths, included paths). The suite files
    under src/tests say which of the sets in Tests.java apply to each Java
    version; without one for this version, every test belongs to the set.
    """
    major = java_major(name or "")
    if major is None:
        return "", [], []
    suite = os.path.join(SCRIPT_DIR, "src", "tests", "extendj", f"TestJava{major}.java")
    sets = parse_test_sets(os.path.join(SCRIPT_DIR, "src", "tests", "Tests.java"))
    includes, excludes = parse_suite(suite, sets)
    if not excludes and not includes:
        return "", [], []
    return os.path.relpath(suite, SCRIPT_DIR), excludes, includes


def in_test_set(rel, excludes, includes):
    """Whether a test belongs to a suite, matched as the test suite matches it."""
    path = rel.replace(os.sep, "/")
    if any(path.startswith(exclude) for exclude in excludes):
        return False
    return not includes or any(path.startswith(include) for include in includes)


def sanitize(name):
    return re.sub(r"[^\w.-]+", "_", name)


class App:
    def __init__(self, stdscr, cfg):
        self.stdscr = stdscr
        self.cfg = cfg
        self.tests_root = cfg["tests_root"]
        self.show_all = False   # show tests outside the version's test set too
        self.reload_tests()     # reads the test set, so it comes after show_all
        self.mode = "main"
        self.cursor = 0   # row
        self.col = 0      # column: 0 is the test, 1..n its compiler results
        self.top = 0
        self.filter = ""
        self.selection = {}  # rel -> selected, inherited by everything below
        self.msg = ""
        self.prompt_line = None

        self.lock = threading.Lock()
        self.runs = [Run() for _ in cfg["versions"]]
        self.run = self.runs[cfg["active"]]
        self.yoff = 1  # the version tab bar occupies the first line
        self.gen = 0
        self.threads = []
        self.workers = set()
        self.canceled = False
        self.runner_classes = None
        self.jvm_error = ""
        self.dirty = threading.Event()
        self.session_dir = tempfile.mkdtemp(prefix="extendj-tui-")
        self.diag_dir = os.path.join(self.session_dir, "diagnostics")
        os.makedirs(self.diag_dir, exist_ok=True)

        self.diag_lines = []
        self.diag_off = 0
        self.diag_title = ""
        self.diag_path = None

        self.ccursor = 0

    # ---------- data helpers ----------

    def reload_tests(self):
        self.tests_root = self.cfg["tests_root"]
        self.tests = discover_tests(self.tests_root)
        self.tree, self.index = build_tree(self.tests)
        self.reload_test_set()

    def reload_test_set(self):
        """Read which tests the suite of the active Java version leaves out."""
        self.test_set = suite_excludes(self.version().get("name", ""))
        _, excludes, includes = self.test_set
        self.excluded = {t.rel for t in self.tests
                         if not in_test_set(t.rel, excludes, includes)}
        self.setcount = {}  # tests of the set under each node

        def count(node):
            total = 0
            if node.test is not None and node.test.rel not in self.excluded:
                total = 1
            for child in node.children:
                total += count(child)
            self.setcount[node.rel] = total
            return total

        count(self.tree)

    def visible(self, stats=None):
        """The rows of the test tree: (node, depth), the ALL TESTS row first.

        The name filter, the result filter and the test set of the active Java
        version all hide rows; the two filters expand the tree so that every
        match is in view.
        """
        out = [(self.tree, 0)]  # the "all tests" item is always the first row
        needle = self.filter.lower()
        results = self.run.filter if stats else "all"
        memo = {}

        def subtree_matches(node):
            cached = memo.get(node.rel)
            if cached is not None:
                return cached
            match = needle in node.rel.lower() or any(
                subtree_matches(c) for c in node.children)
            memo[node.rel] = match
            return match

        def keep(node):
            if not self.show_all and not self.setcount.get(node.rel, 1):
                return False  # nothing here belongs to this version's test set
            if needle and not subtree_matches(node):
                return False
            if results == "diff":
                return stats[node.rel][1] > 0
            if results == "unexpected":
                return stats[node.rel][2] > 0
            return True

        def walk(node, depth):
            for child in node.children:
                if not keep(child):
                    continue
                out.append((child, depth))
                if child.children and (child.expanded or needle or results != "all"):
                    walk(child, depth + 1)

        walk(self.tree, 0)
        return out

    def tests_under(self, node):
        """The tests under a node that the tree is showing."""
        out = []

        def rec(n):
            if n.test and (self.show_all or n.test.rel not in self.excluded):
                out.append(n.test)
            for c in n.children:
                rec(c)

        rec(node)
        return out

    def inherited_selection(self, node):
        """Whether a node is selected by a decision taken further up the tree."""
        node = node.parent
        while node is not None:
            if node.rel in self.selection:
                return self.selection[node.rel]
            node = node.parent
        return False

    def selection_state(self):
        """The selected tests, and per node how many of its tests are selected.

        A directory passes its decision down to everything below it, so
        selecting a directory selects its tests, and deselecting one of them
        afterwards is recorded as an exception to that.
        """
        counts = {}
        tests = []

        hidden = set() if self.show_all else self.excluded

        def visit(node, inherited):
            chosen = self.selection.get(node.rel, inherited)
            nsel = ntests = 0
            if node.test is not None and node.rel not in hidden:
                ntests = 1
                if chosen:
                    nsel = 1
                    tests.append(node.test)
            for child in node.children:
                csel, ctotal = visit(child, chosen)
                nsel += csel
                ntests += ctotal
            counts[node.rel] = (nsel, ntests)
            return nsel, ntests

        visit(self.tree, False)
        return counts, tests

    def selected_tests(self):
        return self.selection_state()[1]

    def failed_tests(self):
        """The tests of the run that did not give the result they are expected to."""
        hidden = set() if self.show_all else self.excluded
        failed = []
        with self.lock:
            for test in self.tests:
                if test.rel in hidden or test.rel not in self.run.rels:
                    continue
                for comp in self.run.comps:
                    result = self.run.results.get((test.rel, comp["name"]))
                    if result is not None and result.done and not result.matches_expected(test):
                        failed.append(test.rel)
                        break
        return failed

    def toggle_selection(self, node):
        """Select or deselect a subtree, keeping only the decisions that matter."""
        inherited = self.inherited_selection(node)
        value = not self.selection.get(node.rel, inherited)
        prefix = node.rel + os.sep
        for rel in [r for r in self.selection
                    if r == node.rel or r.startswith(prefix)]:
            del self.selection[rel]  # this decision replaces the ones below it
        if value != inherited:
            self.selection[node.rel] = value

    # ---------- test running ----------

    def version(self):
        """The active target Java version."""
        return self.cfg["versions"][self.cfg["active"]]

    def java(self):
        return resolve_java(self.version())

    def switch_version(self, index):
        """Show another target Java version, with the results of its last run."""
        versions = self.cfg["versions"]
        while len(self.runs) < len(versions):
            self.runs.append(Run())
        index %= len(versions)
        self.cfg["active"] = index
        self.run = self.runs[index]
        self.reload_test_set()
        self.ccursor = 0
        # Saved right away, so the next run of the tool opens on this version.
        self.msg = (save_config(self.cfg)
                    or f"target Java version: {versions[index]['name']}")

    def enabled_compilers(self):
        return [c for c in self.version()["compilers"] if c.get("enabled", True)]

    def start_run(self, tests):
        comps = self.enabled_compilers()
        if not comps:
            self.msg = "no enabled compilers - press c to configure"
            return
        if not tests:
            self.msg = "no tests in the selected set"
            return
        self.stop_workers()
        self.gen += 1
        gen = self.gen
        self.canceled = False
        # Keep what earlier runs found out about the tests this run does not cover.
        run = self.run
        tests = sorted(tests, key=lambda t: t.rel)
        run.comps = [dict(c) for c in comps]
        for comp in run.comps:
            comp["_spec"] = jvm_spec(comp)
        pairs = [(test, index) for test in tests
                 for index, comp in enumerate(run.comps) if compiles_test(comp, test)]
        run.rels.update(test.rel for test, _ in pairs)
        with self.lock:
            for test, index in pairs:
                run.results[(test.rel, run.comps[index]["name"])] = Result()

        self.runner_classes = None
        self.jvm_error = ""
        if any(c["_spec"] for c in run.comps):
            self.runner_classes, self.jvm_error = build_runner(self.java())

        jobs = queue.Queue()
        for pair in pairs:
            jobs.put(pair)
        nworkers = max(1, min(int(self.cfg.get("jobs", MAX_JOBS)), MAX_JOBS, len(pairs)))
        self.threads = []
        for _ in range(nworkers):
            thread = threading.Thread(target=self.worker_loop, args=(run, gen, jobs),
                                      daemon=True)
            thread.start()
            self.threads.append(thread)

        self.msg = self.jvm_error

    def compile_args(self, test, comp, tmpdir):
        """Compiler arguments for one test, without the compiler command itself."""
        classpath = test.props.get("classpath", "") or test.dir
        # The same variables the regression test suite expands, and the jar of
        # the ExtendJ under test where a test asks for the compiler itself.
        jar = comp.get("classpath", "") if comp.get("type") == "extendj" else ""
        if not jar:
            jar = next((c.get("classpath", "") for c in self.enabled_compilers()
                        if c.get("type") == "extendj"), "")
        classpath = (classpath
                     .replace("@TEST_DIR@", test.dir)
                     .replace("@TMP_DIR@", tmpdir)
                     .replace("@TEMP_DIR@", tmpdir)
                     .replace("@EXTENDJ_LIB@", jar)
                     .replace("@RUNTIME_CLASSES@",
                              os.path.join(SCRIPT_DIR, "runtime", "classes")))
        # Options the test asks for, as the test suite passes them.
        options = ["-" + option.strip()
                   for option in test.props.get("options", "").split(",")
                   if option.strip()]
        sourcepath = test.props.get("sourcepath", "").strip()
        if sourcepath:
            options += ["-sourcepath", (sourcepath
                                        .replace("@TEST_DIR@", test.dir)
                                        .replace("@TMP_DIR@", tmpdir))]
        return (shlex.split(comp.get("flags", ""))
                + ["-d", tmpdir, "-classpath", classpath] + options
                + [os.path.join(test.dir, s) for s in test.sources])

    def build_cmd(self, test, comp, tmpdir):
        """Command line for compiling a test in its own process."""
        args = self.compile_args(test, comp, tmpdir)
        if comp.get("type") == "javac":
            return [comp.get("program", "javac")] + args
        return ([self.java(), "-cp", comp.get("classpath", "extendj.jar"),
                 "org.extendj.JavaCompiler"] + args)

    def worker_loop(self, run, gen, jobs):
        """Run compile jobs, keeping one TestRunner worker JVM alive for this thread."""
        worker = None
        try:
            while not self.canceled and gen == self.gen:
                try:
                    test, index = jobs.get_nowait()
                except queue.Empty:
                    return
                comp = run.comps[index]
                use_jvm = comp["_spec"] and self.runner_classes and not self.jvm_error
                if use_jvm and worker is None:
                    worker, error = self.start_worker(run)
                    if worker is None:
                        self.jvm_error = error
                        use_jvm = False
                if use_jvm:
                    worker = self.run_jvm_job(run, worker, test, comp, index, gen)
                else:
                    self.run_process_job(run, test, comp, gen)
        finally:
            if worker is not None:
                self.stop_worker(worker)

    def start_worker(self, run):
        """Start a worker JVM loaded with every compiler version of the run."""
        cmd = [self.java(), "-cp", self.runner_classes, "TestRunner", "--worker"]
        for comp in run.comps:
            cmd.extend(comp["_spec"] or ("extendj", ""))
        try:
            worker = JvmWorker(cmd, self.session_dir)
            worker.start(float(self.cfg.get("timeout", 60)))
        except Exception as exc:  # noqa: BLE001 - fall back to one process per test
            return None, f"worker JVM failed ({exc}) - running tests in separate JVMs"
        with self.lock:
            self.workers.add(worker)
        return worker, ""

    def stop_worker(self, worker):
        with self.lock:
            self.workers.discard(worker)
        worker.stop()

    def run_jvm_job(self, run, worker, test, comp, index, gen):
        """Compile one test in the worker JVM. Returns the worker to keep using."""
        if not self.begin_job(run, test, comp, gen):
            return worker
        tmpdir = tempfile.mkdtemp(dir=self.session_dir)
        args = self.compile_args(test, comp, tmpdir)
        # Report the standalone command line: it is what reproduces this compilation.
        cmd = shlex.join(self.build_cmd(test, comp, tmpdir))
        start = time.time()
        try:
            code, output = worker.compile(index, args, float(self.cfg.get("timeout", 60)))
            status = "pass" if code == 0 else "fail"
        except WorkerTimeout:
            status, code = "timeout", None
            output = f"compile timed out after {self.cfg.get('timeout', 60)}s"
        except WorkerDead as exc:
            status, code = "error", None
            output = f"worker JVM died: {exc}"
        finally:
            shutil.rmtree(tmpdir, ignore_errors=True)
        self.finish_job(run, test, comp, gen, status, output, cmd, code, time.time() - start)
        if status in ("timeout", "error"):
            # The worker is in an unknown state: replace it for the next job.
            self.stop_worker(worker)
            return None
        return worker

    def run_process_job(self, run, test, comp, gen):
        """Compile one test in its own process."""
        if not self.begin_job(run, test, comp, gen):
            return
        tmpdir = tempfile.mkdtemp(dir=self.session_dir)
        cmd = self.build_cmd(test, comp, tmpdir)
        status, output, code = "error", "", None
        start = time.time()
        try:
            proc = subprocess.run(cmd, capture_output=True, text=True,
                                  timeout=float(self.cfg.get("timeout", 60)))
            code = proc.returncode
            output = "\n".join(part.rstrip("\n") for part in (proc.stdout, proc.stderr) if part)
            status = "pass" if code == 0 else "fail"
        except subprocess.TimeoutExpired:
            status = "timeout"
            output = f"compile timed out after {self.cfg.get('timeout', 60)}s"
        except Exception as exc:  # noqa: BLE001 - report launch failures in the UI
            status = "error"
            output = str(exc)
        finally:
            shutil.rmtree(tmpdir, ignore_errors=True)
        self.finish_job(run, test, comp, gen, status, output, shlex.join(cmd), code,
                        time.time() - start)

    def begin_job(self, run, test, comp, gen):
        with self.lock:
            if gen != self.gen:
                return False
            result = run.results.get((test.rel, comp["name"]))
            if result is None or result.status == "canceled":
                return False
            result.status = "running"
        self.dirty.set()
        return True

    def finish_job(self, run, test, comp, gen, status, output, cmd, code, elapsed):
        diag = compare_diagnostics(test, comp, output)[0] if status in ("pass", "fail") else ""
        path = os.path.join(self.diag_dir,
                            f"{sanitize(test.rel)}__{sanitize(comp['name'])}.txt")
        try:
            with open(path, "w") as fp:
                fp.write(f"$ {cmd}\nexit: {code}   time: {elapsed:.2f}s\n\n{output}\n")
        except OSError:
            path = ""
        with self.lock:
            if gen != self.gen:
                return
            result = run.results.get((test.rel, comp["name"]))
            if result is None:
                return
            result.status = status
            result.diag = diag
            result.output = output
            result.cmd = cmd
            result.code = code
            result.elapsed = elapsed
            result.path = path
        self.dirty.set()

    def stop_workers(self):
        self.canceled = True
        with self.lock:
            workers = list(self.workers)
            self.workers.clear()
        for worker in workers:
            worker.stop()

    def cancel_pending(self):
        self.stop_workers()
        with self.lock:
            for result in self.run.results.values():
                if result.status in ("pending", "running"):
                    result.status = "canceled"
        self.msg = "pending jobs canceled"

    # ---------- results helpers ----------

    def run_stats(self):
        """Aggregate the results of the active run over the whole test tree.

        Maps each node to (tests, differing tests, unexpected tests, cells),
        where cells holds (done, total, unexpected) per compiler. Only tests
        that are part of the run are counted, so a node covered by no run has
        no test in its totals.
        """
        stats = {}
        run = self.run
        ncomp = len(run.comps)
        empty = [(0, 0, 0)] * ncomp

        def own(test):
            """Stats for a single test: it may sit in a directory with nested tests."""
            cells = []
            statuses = []
            bad = 0
            for comp in run.comps:
                result = run.results.get((test.rel, comp["name"]))
                if result is None:
                    cells.append((0, 0, 0))
                    continue
                if not result.done:
                    cells.append((0, 1, 0))
                    continue
                statuses.append(result.status)
                off = 0 if result.matches_expected(test) else 1
                bad += off
                cells.append((1, 1, off))
            return (1, 1 if len(set(statuses)) > 1 else 0, 1 if bad else 0, cells)

        def visit(node):
            if node.test is not None and node.test.rel in run.rels:
                tests, ndiff, nbad, cells = own(node.test)
            else:
                tests = ndiff = nbad = 0
                cells = empty
            for child in node.children:
                visit(child)
                ctests, cdiff, cbad, ccells = stats[child.rel]
                tests += ctests
                ndiff += cdiff
                nbad += cbad
                cells = [(a[0] + b[0], a[1] + b[1], a[2] + b[2])
                         for a, b in zip(cells, ccells)]
            stats[node.rel] = (tests, ndiff, nbad, cells)

        with self.lock:
            visit(self.tree)
        return stats

    # ---------- drawing ----------

    def color(self, name):
        return curses.color_pair(self.pairs[name])

    def init_colors(self):
        curses.start_color()
        curses.use_default_colors()
        names = ["green", "red", "yellow", "cyan", "magenta", "blue"]
        colors = [curses.COLOR_GREEN, curses.COLOR_RED, curses.COLOR_YELLOW,
                  curses.COLOR_CYAN, curses.COLOR_MAGENTA, curses.COLOR_BLUE]
        self.pairs = {}
        for i, (name, color) in enumerate(zip(names, colors), start=1):
            curses.init_pair(i, color, -1)
            self.pairs[name] = i

    def put(self, y, x, text, attr=0, raw=False):
        """Draw text below the version tab bar, or over it when raw is set."""
        height, width = self.stdscr.getmaxyx()
        if not raw:
            y += self.yoff
        if y < 0 or y >= height or x >= width:
            return
        try:
            self.stdscr.addnstr(y, x, text, max(0, width - x), attr)
        except curses.error:
            pass

    def view_size(self):
        """The size of the screen area below the version tab bar."""
        height, width = self.stdscr.getmaxyx()
        return height - self.yoff, width

    def draw_tabs(self, width):
        """One tab per configured target Java version, the active one highlighted."""
        x = 0
        for i, version in enumerate(self.cfg["versions"]):
            label = f" {version['name']} "
            active = i == self.cfg["active"]
            self.put(0, x, label,
                     (curses.A_REVERSE | curses.A_BOLD) if active
                     else self.color("cyan"), raw=True)
            x += len(label)
            if i + 1 < len(self.cfg["versions"]):
                self.put(0, x, "│", curses.A_DIM, raw=True)
                x += 1
        hint = "tab: switch Java version "
        self.put(0, max(x + 1, width - len(hint)), hint, curses.A_DIM, raw=True)

    def draw(self):
        self.stdscr.erase()
        height, width = self.view_size()
        self.draw_tabs(width)
        if self.mode == "main":
            self.draw_main(height, width)
        elif self.mode == "diag":
            self.draw_diag(height, width)
        elif self.mode == "config":
            self.draw_config(height, width)
        if self.prompt_line is not None:
            self.put(height - 1, 0, self.prompt_line, curses.A_BOLD)
            try:
                self.stdscr.move(height - 1 + self.yoff,
                                 min(width - 1, len(self.prompt_line)))
            except curses.error:
                pass
        self.stdscr.refresh()

    def draw_scrollbar(self, y0, body, total, top):
        """Draw a vertical scrollbar in the last column next to a body of rows."""
        if total <= body or body < 1:
            return
        x = self.stdscr.getmaxyx()[1] - 1
        size = max(1, body * body // total)
        maxtop = total - body
        pos = min(body - size, top * (body - size + 1) // maxtop) if maxtop else 0
        for i in range(body):
            if pos <= i < pos + size:
                self.put(y0 + i, x, "█")
            else:
                self.put(y0 + i, x, "░", curses.A_DIM)

    def footer(self, height, width, hints):
        text = self.msg if self.msg else hints
        self.put(height - 1, 0, text[:width - 1], curses.A_DIM)

    def leaf_cell(self, test, comp):
        """Cell text and attribute for one compilation."""
        with self.lock:
            result = self.run.results.get((test.rel, comp["name"]))
        if result is None:
            return "", 0
        status = result.status
        if status in ("pass", "fail"):
            attr = (self.color("green") if result.matches_expected(test)
                    else self.color("red") | curses.A_BOLD)
        elif status in ("timeout", "error"):
            attr = self.color("magenta") | curses.A_BOLD
        elif status == "running":
            attr = self.color("yellow")
        else:
            attr = curses.A_DIM
        text = CELL_TEXT.get(status, status)
        if result.diag == "differ":
            text += "!"  # the messages are not the ones the test expects
        return text, attr

    def summary_cell(self, cell):
        """Cell text and attribute summarizing a directory."""
        done, total, bad = cell
        if done < total:
            return f"{done}/{total}", curses.A_DIM
        if bad:
            return f"✗{bad}", self.color("red") | curses.A_BOLD
        return "✓", self.color("green")

    def draw_main(self, height, width):
        """The test tree, with the results of the active run in columns."""
        stats = self.run_stats()
        selcount, seltests = self.selection_state()
        rows = self.visible(stats)
        comps = self.run.comps or self.enabled_compilers()
        ntests, ndiff, nbad, cells = stats[self.tree.rel]
        done = sum(c[0] for c in cells)
        total = sum(c[1] for c in cells)

        header = f"{selcount[self.tree.rel][1]} tests"
        if self.excluded:
            header += (f"   set: all, - is outside {self.test_set[0]}" if self.show_all
                       else f"   set: {self.test_set[0]}")
        if self.run.filter != "all":
            header += f"   only: {self.run.filter}"
        if self.filter:
            header += f"   find: {self.filter}"
        nsel = len(seltests)
        if nsel:
            header += f"   selected: {nsel}"
        if self.run.rels:
            header += f"   results: {ntests} done {done}/{total}"
        counts = f"diff: {ndiff}  unexpected: {nbad}" if self.run.rels else ""
        self.put(0, 0, header[:max(0, width - len(counts) - 2)], curses.A_BOLD)
        if counts:
            self.put(0, max(0, width - len(counts)), counts, curses.A_BOLD)

        labels = []
        for node, depth in rows:
            rdiff = stats[node.rel][1]
            picked, under = selcount[node.rel]
            if node is self.tree:
                label = f"ALL TESTS ({under})"
            elif node.children:
                open_ = node.expanded or self.filter or self.run.filter != "all"
                label = f"{'▾' if open_ else '▸'} {node.name} ({under})"
            else:
                label = "  " + node.name  # what it expects is in the exp column
            # Marker columns: selected for the next run, compilers disagree, and
            # while every test is shown, outside the test set of this version.
            if node is self.tree or not picked:
                mark = " "
            else:
                mark = "*" if picked == under else "~"
            marker = mark + ("≠" if rdiff else " ")
            if self.show_all:
                marker += "-" if not self.setcount.get(node.rel, 1) else " "
            labels.append(marker + "  " * depth + label)
        namew = max(20, min(46, max([len(t) for t in labels] or [0]) + 1))
        colw = [max(6, min(14, len(c["name"]) + 2)) for c in comps]

        self.put(1, 0, "test".ljust(namew),
                 curses.A_UNDERLINE | (curses.A_BOLD if self.col == 0 else 0))
        self.put(1, namew, " exp ", curses.A_UNDERLINE)
        x = namew + 6
        for j, comp in enumerate(comps):
            attr = curses.A_UNDERLINE | (curses.A_BOLD if j + 1 == self.col else 0)
            self.put(1, x, comp["name"][:colw[j] - 1].center(colw[j]), attr)
            x += colw[j]

        body = height - 3
        self.cursor = max(0, min(self.cursor, len(rows) - 1))
        self.col = max(0, min(self.col, len(comps)))
        if self.cursor < self.top:
            self.top = self.cursor
        if self.cursor >= self.top + body:
            self.top = self.cursor - body + 1
        if len(rows) <= 1:
            self.put(3, 2, "(no tests match)", curses.A_DIM)

        for i, (node, depth) in enumerate(rows[self.top:self.top + body]):
            y = 2 + i
            row = self.top + i
            on_row = row == self.cursor
            name_attr = curses.A_REVERSE if (on_row and self.col == 0) else 0
            if on_row and self.col:
                name_attr |= curses.A_BOLD
            if node.rel in self.excluded:
                name_attr |= curses.A_DIM
            if labels[row][0] == "*":
                name_attr |= self.color("cyan") | curses.A_BOLD
            elif stats[node.rel][2]:
                name_attr |= curses.A_BOLD
            self.put(y, 0, labels[row][:namew].ljust(namew), name_attr)
            in_run = node.test is not None and node.test.rel in self.run.rels
            if node.test is not None:
                self.put(y, namew, "fail" if node.test.expected_fail else "pass",
                         curses.A_DIM)
            x = namew + 6
            cells = stats[node.rel][3]
            for j, comp in enumerate(comps):
                if in_run and j < len(self.run.comps):
                    text, attr = self.leaf_cell(node.test, comp)
                elif node.test is None and j < len(cells) and cells[j][1]:
                    text, attr = self.summary_cell(cells[j])
                else:
                    text, attr = "", 0
                if on_row and j + 1 == self.col:
                    attr |= curses.A_REVERSE
                self.put(y, x, text[:colw[j]].center(colw[j]), attr)
                x += colw[j]
        self.draw_scrollbar(2, body, len(rows), self.top)
        hints = (f"space:select  {'f:failed  ' if nbad else ''}"
                 f"{'u:clear  ' if nsel else ''}r:run  enter:open  h/l:column  "
                 "/:find  o:filter  a:all  c:config  q:quit")
        if done < total:
            hints = "running - x:cancel   " + hints
        self.footer(height, width, hints)

    def draw_diag(self, height, width):
        self.put(0, 0, self.diag_title[:width - 1], curses.A_BOLD | curses.A_REVERSE)
        body = height - 1 - (1 if self.msg else 0)
        maxoff = max(0, len(self.diag_lines) - body)
        self.diag_off = max(0, min(self.diag_off, maxoff))
        for i, line in enumerate(self.diag_lines[self.diag_off:self.diag_off + body]):
            self.put(1 + i, 0, line)
        self.draw_scrollbar(1, body, len(self.diag_lines), self.diag_off)
        if self.msg:
            self.put(height - 1, 0, self.msg[:width - 1], curses.A_BOLD)

    def config_rows(self):
        rows = [("compiler", i) for i in range(len(self.version()["compilers"]))]
        rows.append(("add", None))
        rows.append(("name", None))
        rows.append(("java", None))
        rows.append(("timeout", None))
        rows.append(("jobs", None))
        rows.append(("tests_root", None))
        rows.append(("addversion", None))
        rows.append(("delversion", None))
        return rows

    def describe_java(self):
        """What the active version's java setting resolves to, and its version."""
        version = self.version()
        java = resolve_java(version)
        wanted = java_major(version.get("name", ""))
        if java == "java":
            if wanted is None:
                return "java on the path"
            return f"java on the path (no Java {wanted} runtime found)"
        found = java_home_version(os.path.dirname(os.path.dirname(java))) or "?"
        return f"{java} (Java {found})"

    def config_value(self, kind):
        if kind in ("name", "java"):
            return self.version().get(kind, "")
        return self.cfg.get(kind, "")

    def draw_config(self, height, width):
        version = self.version()
        self.put(0, 0, f"Configuration of Java version '{version['name']}' "
                 f"({CONFIG_FILE})", curses.A_BOLD)
        rows = self.config_rows()
        self.ccursor = max(0, min(self.ccursor, len(rows) - 1))
        y = 2
        self.put(y, 0, "Compilers:", curses.A_UNDERLINE)
        y += 1
        for idx, (kind, i) in enumerate(rows):
            attr = curses.A_REVERSE if idx == self.ccursor else 0
            if kind == "compiler":
                comp = version["compilers"][i]
                enabled = "x" if comp.get("enabled", True) else " "
                if comp.get("type") == "javac":
                    detail = f"javac    program={comp.get('program', 'javac')}"
                else:
                    detail = f"extendj  jar={comp.get('classpath', '')}"
                flags = comp.get("flags", "")
                if flags:
                    detail += f"  flags={flags}"
                detail += f"  [{'shared JVM' if jvm_spec(comp) else 'own process'}]"
                self.put(y, 0, f" [{enabled}] {comp['name']:<16} {detail}", attr)
            elif kind == "add":
                self.put(y, 0, "  + add compiler", attr | self.color("cyan"))
                y += 1
                self.put(y, 0, "Settings:", curses.A_UNDERLINE)
            elif kind == "addversion":
                y += 1
                self.put(y, 0, "Java versions:", curses.A_UNDERLINE)
                y += 1
                self.put(y, 0, "  + add Java version", attr | self.color("cyan"))
            elif kind == "delversion":
                self.put(y, 0, f"  - delete Java version '{version['name']}'",
                         attr | self.color("cyan"))
            else:
                label = {"name": "version name", "java": "java command",
                         "timeout": "timeout (s)", "jobs": "parallel jobs",
                         "tests_root": "tests root"}[kind]
                value = str(self.config_value(kind))
                if kind == "java":
                    value += f"   -> {self.describe_java()}"
                elif kind == "jobs":
                    value += f"   (at most {MAX_JOBS}, half the cores of this machine)"
                self.put(y, 0, f"  {label:<14} = {value}", attr)
            y += 1
        self.footer(height, width,
                    "space:enable  enter/e:edit  a:add  d:delete  J/K:move  q:back")

    # ---------- input ----------

    def prompt(self, label, initial="", live=None):
        buf = list(initial)
        self.stdscr.timeout(-1)
        curses.curs_set(1)
        try:
            while True:
                self.prompt_line = f"{label} {''.join(buf)}"
                self.draw()
                try:
                    ch = self.stdscr.get_wch()
                except curses.error:
                    continue
                if ch in ("\n", "\r", curses.KEY_ENTER):
                    return "".join(buf)
                if ch == "\x1b":
                    return None
                if ch in ("\x7f", "\x08", curses.KEY_BACKSPACE):
                    if buf:
                        buf.pop()
                elif ch == "\x15":  # ctrl-u
                    buf.clear()
                elif isinstance(ch, str) and ch.isprintable():
                    buf.append(ch)
                if live is not None:
                    live("".join(buf))
        finally:
            self.prompt_line = None
            curses.curs_set(0)
            self.stdscr.timeout(100)

    def confirm(self, question):
        answer = self.prompt(question + " (y/n)")
        return answer is not None and answer.strip().lower().startswith("y")

    # ---------- key handling per mode ----------

    def show_text(self, title, raw, path=None):
        """Show text fullscreen, wrapped to the screen width.

        A path marks the text as the contents of a file, which e then edits.
        """
        width = self.view_size()[1]
        lines = []
        for line in raw:
            line = line.expandtabs(4)
            while len(line) > width - 1:
                lines.append(line[:width - 1])
                line = line[width - 1:]
            lines.append(line)
        self.diag_lines = lines
        self.diag_off = 0
        self.diag_path = path
        self.diag_title = f"{title} (q to go back{', e to edit' if path else ''})"
        self.mode = "diag"

    def open_source(self, test):
        """Show a test's Java source, fullscreen."""
        name = "Test.java" if "Test.java" in test.sources else test.sources[0]
        path = os.path.join(test.dir, name)
        self.show_text(path, read_source(path) or [f"({name} is empty)"], path)

    def open_diag(self, test, comp):
        """Show the captured diagnostics of one test and compiler, fullscreen."""
        with self.lock:
            result = self.run.results.get((test.rel, comp["name"]))
        if result is None:
            self.msg = (f"{test.rel} has no result for {comp['name']} in Java version "
                        f"'{self.version()['name']}'")
            return
        if result.status in ("pending", "running", "canceled"):
            self.msg = f"no diagnostics yet ({result.status})"
            return
        header = [f"$ {result.cmd}",
                  f"exit: {result.code}   time: {result.elapsed:.2f}s   "
                  f"saved: {result.path or '-'}"]
        if result.diag:
            state, expected, actual = compare_diagnostics(test, comp, result.output)
            header.append(f"messages {'match' if state == 'match' else 'DIFFER from'} "
                          f"{os.path.relpath(test.expected_err, self.tests_root)}")
            if state == "differ":
                header.append("")
                header.extend(difflib.unified_diff(expected, actual, "expected",
                                                   "actual", lineterm="", n=99))
        self.show_text(f"{test.rel} | {comp['name']} | {result.status.upper()}",
                       header + [""]
                       + (result.output.splitlines() or ["(no compiler output)"]))

    def handle_main(self, ch):
        rows = self.visible(self.run_stats())
        node = rows[self.cursor][0] if rows and self.cursor < len(rows) else None
        quarter = max(1, (self.view_size()[0] - 3) // 4)
        if ch in (curses.KEY_DOWN, ord("j")):
            self.cursor += 1
        elif ch in (curses.KEY_UP, ord("k")):
            self.cursor -= 1
        elif ch == curses.KEY_NPAGE:
            self.cursor += 20
        elif ch == curses.KEY_PPAGE:
            self.cursor -= 20
        elif ch == 4:  # Ctrl-D: quarter screen down
            self.cursor += quarter
        elif ch == 21:  # Ctrl-U: quarter screen up
            self.cursor -= quarter
        elif ch == ord("g"):
            self.cursor = 0
        elif ch == ord("G"):
            self.cursor = len(rows) - 1
        elif ch in (curses.KEY_RIGHT, ord("l")):
            self.col += 1
        elif ch in (curses.KEY_LEFT, ord("h")):
            if self.col:
                self.col -= 1
            elif node is not None and node.parent is not None:
                for i, (parent, _) in enumerate(rows):
                    if parent is node.parent:
                        self.cursor = i
                        break
        elif ch in (ord("\n"), curses.KEY_ENTER, 10, 13):
            comps = self.run.comps or self.enabled_compilers()
            if node is None:
                pass
            elif node.children or node is self.tree:
                node.expanded = not node.expanded
            elif self.col == 0:
                self.open_source(node.test)
            elif self.col <= len(comps):
                self.open_diag(node.test, comps[self.col - 1])
        elif ch == ord(" "):
            if node is self.tree:
                self.msg = "ALL TESTS is not selectable - press r on it to run everything"
            elif node:
                self.toggle_selection(node)
        elif ch == ord("u"):
            self.selection.clear()
        elif ch == ord("f"):
            failed = self.failed_tests()
            self.selection = {rel: True for rel in failed}
            self.msg = (f"selected the {len(failed)} tests that did not give the "
                        "expected result" if failed
                        else "no test of this run gave an unexpected result")
        elif ch == ord("a"):
            self.show_all = not self.show_all
            self.msg = ("showing every test" if self.show_all
                        else f"showing the tests of {self.test_set[0] or 'the suite'}")
        elif ch == ord("z"):
            for n in self.index.values():
                n.expanded = False
        elif ch == ord("Z"):
            for n in self.index.values():
                n.expanded = True
        elif ch == ord("/"):
            def live(s):
                self.filter = s
                self.cursor = 0
                self.top = 0
            if self.prompt("/", self.filter, live=live) is None:
                self.filter = ""
        elif ch == 27:  # Esc
            self.filter = ""
            self.run.filter = "all"
        elif ch == ord("o"):
            order = ["all", "diff", "unexpected"]
            self.run.filter = order[(order.index(self.run.filter) + 1) % len(order)]
            self.cursor = self.top = 0
        elif ch == ord("r"):
            tests = self.selected_tests()
            if not tests and node:
                tests = self.tests_under(node)
            self.start_run(tests)
        elif ch == ord("x"):
            self.cancel_pending()
        elif ch == ord("c"):
            self.mode = "config"
        elif ch == ord("q"):
            return False
        self.cursor = max(0, self.cursor)
        self.col = max(0, min(self.col, len(self.run.comps or self.enabled_compilers())))
        return True

    def edit_file(self):
        """Edit the file being viewed in the terminal editor, then show it again."""
        if not self.diag_path:
            self.msg = "there is no file to edit here"
            return
        editor = os.environ.get("VISUAL") or os.environ.get("EDITOR") or "vi"
        path = self.diag_path
        offset = self.diag_off
        error = ""
        curses.def_prog_mode()
        curses.endwin()  # hand the terminal over to the editor
        try:
            subprocess.call(shlex.split(editor) + [path])
        except (OSError, ValueError) as exc:
            error = f"cannot run {editor}: {exc}"
        finally:
            curses.reset_prog_mode()
            curses.curs_set(0)
            self.stdscr.clear()
            self.stdscr.refresh()
        self.show_text(path, read_source(path) or ["(empty)"], path)
        self.diag_off = min(offset, max(0, len(self.diag_lines) - 1))
        self.msg = error

    def handle_diag(self, ch):
        page = self.view_size()[0] - 2
        if ch in (curses.KEY_DOWN, ord("j")):
            self.diag_off += 1
        elif ch in (curses.KEY_UP, ord("k")):
            self.diag_off -= 1
        elif ch in (curses.KEY_NPAGE, ord(" ")):
            self.diag_off += page
        elif ch == curses.KEY_PPAGE:
            self.diag_off -= page
        elif ch == 4:  # Ctrl-D: quarter screen down
            self.diag_off += max(1, page // 4)
        elif ch == 21:  # Ctrl-U: quarter screen up
            self.diag_off -= max(1, page // 4)
        elif ch == ord("g"):
            self.diag_off = 0
        elif ch == ord("G"):
            self.diag_off = len(self.diag_lines)
        elif ch == ord("e"):
            self.edit_file()
        elif ch in (ord("q"), 27):
            self.mode = "main"
        self.diag_off = max(0, self.diag_off)
        return True

    DIALOG_FIELDS = ["name", "type", "path", "flags", "enabled"]

    def dialog_field(self, work, key):
        """Return (label, value) for a dialog field."""
        if key == "path":
            if work["type"] == "javac":
                return "javac program", work["program"]
            return "extendj jar", work["classpath"]
        if key == "type":
            return "type", f"< {work['type']} >"
        if key == "enabled":
            return "enabled", "[x]" if work["enabled"] else "[ ]"
        return key, str(work.get(key, ""))

    def draw_compiler_dialog(self, title, work, focus, button, editing, buf, error):
        self.draw()
        height, width = self.view_size()
        fields = self.DIALOG_FIELDS
        bw = min(66, max(40, width - 4))
        bh = len(fields) + 4
        y0 = max(0, (height - bh) // 2)
        x0 = max(0, (width - bw) // 2)
        self.put(y0, x0, "┌" + f" {title} ".center(bw - 2, "─") + "┐", curses.A_BOLD)
        for i in range(1, bh - 1):
            self.put(y0 + i, x0, "│" + " " * (bw - 2) + "│")
        self.put(y0 + bh - 1, x0, "└" + "─" * (bw - 2) + "┘")
        cursor_pos = None
        for i, key in enumerate(fields):
            y = y0 + 1 + i
            label, value = self.dialog_field(work, key)
            if editing and i == focus:
                value = buf
            label_s = f"{label:>13}: "
            maxval = max(1, bw - 4 - len(label_s))
            shown = value[-maxval:]
            self.put(y, x0 + 2, label_s, curses.A_BOLD if i == focus else 0)
            attr = curses.A_REVERSE if (i == focus and not editing) else 0
            self.put(y, x0 + 2 + len(label_s), shown.ljust(maxval)[:maxval], attr)
            if editing and i == focus:
                cursor_pos = (y, min(x0 + bw - 3, x0 + 2 + len(label_s) + len(shown)))
        if error:
            self.put(y0 + len(fields) + 1, x0 + 2, error[:bw - 4],
                     self.color("red") | curses.A_BOLD)
        on_buttons = focus == len(fields)
        save = "> Save <" if (on_buttons and button == 0) else "  Save  "
        cancel = "> Cancel <" if (on_buttons and button == 1) else "  Cancel  "
        bx = x0 + (bw - len(save) - len(cancel) - 3) // 2
        by = y0 + bh - 2
        self.put(by, bx, save,
                 (curses.A_REVERSE | curses.A_BOLD) if (on_buttons and button == 0) else 0)
        self.put(by, bx + len(save) + 3, cancel,
                 (curses.A_REVERSE | curses.A_BOLD) if (on_buttons and button == 1) else 0)
        if cursor_pos:
            curses.curs_set(1)
            try:
                self.stdscr.move(cursor_pos[0] + self.yoff, cursor_pos[1])
            except curses.error:
                pass
        else:
            curses.curs_set(0)
        self.stdscr.refresh()

    def compiler_dialog(self, comp, taken_names):
        """Modal dialog editing all settings of one compiler.

        Edits a working copy; only the Save button applies changes to comp.
        Returns True if saved.
        """
        title = "Edit compiler" if comp.get("name") else "Add compiler"
        work = {
            "name": comp.get("name", ""),
            "type": comp.get("type", "extendj"),
            "program": comp.get("program", "javac"),
            "classpath": comp.get("classpath", os.path.join(SCRIPT_DIR, "extendj.jar")),
            "flags": comp.get("flags", ""),
            "enabled": comp.get("enabled", True),
        }
        fields = self.DIALOG_FIELDS
        focus, button = 0, 0
        editing, buf, error = False, "", ""
        self.stdscr.timeout(-1)
        try:
            while True:
                self.draw_compiler_dialog(title, work, focus, button, editing, buf, error)
                try:
                    ch = self.stdscr.get_wch()
                except curses.error:
                    continue
                if ch == curses.KEY_RESIZE:
                    continue
                key = fields[focus] if focus < len(fields) else "buttons"
                if editing:
                    if ch in ("\n", "\r") or ch == curses.KEY_ENTER:
                        text = buf if key == "flags" else buf.strip()
                        if key == "path":
                            field = "program" if work["type"] == "javac" else "classpath"
                            work[field] = os.path.expanduser(text)
                        else:
                            work[key] = text
                        editing = False
                    elif ch == "\x1b":
                        editing = False
                    elif ch in ("\x7f", "\x08") or ch == curses.KEY_BACKSPACE:
                        buf = buf[:-1]
                    elif ch == "\x15":  # ctrl-u
                        buf = ""
                    elif isinstance(ch, str) and ch.isprintable():
                        buf += ch
                    continue
                if ch == "\x1b":
                    return False
                if ch in ("j", "\t") or ch == curses.KEY_DOWN:
                    focus = min(focus + 1, len(fields))
                elif ch == "k" or ch == curses.KEY_UP or ch == curses.KEY_BTAB:
                    focus = max(focus - 1, 0)
                elif ch in ("h", "l") or ch in (curses.KEY_LEFT, curses.KEY_RIGHT):
                    if key == "buttons":
                        button = 1 - button
                    elif key == "type":
                        work["type"] = "javac" if work["type"] == "extendj" else "extendj"
                elif ch in ("\n", "\r", " ") or ch == curses.KEY_ENTER:
                    if key == "type":
                        work["type"] = "javac" if work["type"] == "extendj" else "extendj"
                    elif key == "enabled":
                        work["enabled"] = not work["enabled"]
                    elif key == "buttons":
                        if button == 1:
                            return False
                        name = work["name"].strip()
                        if not name:
                            error, focus = "name must not be empty", 0
                        elif name in taken_names:
                            error, focus = f"name '{name}' is already used", 0
                        else:
                            comp.update({
                                "name": name,
                                "type": work["type"],
                                "program": work["program"],
                                "classpath": work["classpath"],
                                "flags": work["flags"],
                                "enabled": work["enabled"],
                            })
                            return True
                    elif ch != " ":  # Enter starts editing text fields
                        _, value = self.dialog_field(work, key)
                        editing, buf, error = True, value, ""
        finally:
            curses.curs_set(0)
            self.stdscr.timeout(100)

    def handle_config(self, ch):
        rows = self.config_rows()
        kind, idx = rows[self.ccursor]
        comps = self.version()["compilers"]
        changed = False
        if ch in (curses.KEY_DOWN, ord("j")):
            self.ccursor += 1
        elif ch in (curses.KEY_UP, ord("k")):
            self.ccursor -= 1
        elif ch == ord(" ") and kind == "compiler":
            comps[idx]["enabled"] = not comps[idx].get("enabled", True)
            changed = True
        elif ch in (ord("\n"), curses.KEY_ENTER, 10, 13, ord("e")):
            if kind == "compiler":
                others = {c["name"] for j, c in enumerate(comps) if j != idx}
                changed = self.compiler_dialog(comps[idx], others)
            elif kind == "add":
                comp = {}
                if self.compiler_dialog(comp, {c["name"] for c in comps}):
                    comps.append(comp)
                    changed = True
            elif kind in ("name", "java", "tests_root"):
                value = self.prompt(kind + ":", str(self.config_value(kind)))
                if value is not None and value.strip():
                    value = os.path.expanduser(value.strip())
                    if kind in ("name", "java"):
                        self.version()[kind] = value
                    else:
                        self.cfg[kind] = value
                    changed = True
                    if kind == "tests_root":
                        self.reload_tests()
                        self.cursor = self.top = 0
                        self.selection.clear()
            elif kind in ("timeout", "jobs"):
                value = self.prompt(kind + ":", str(self.cfg.get(kind, "")))
                if value is not None:
                    try:
                        number = max(1, int(value.strip()))
                    except ValueError:
                        self.msg = f"not a number: {value}"
                    else:
                        if kind == "jobs" and number > MAX_JOBS:
                            number = MAX_JOBS
                            self.msg = (f"at most {MAX_JOBS} jobs, half the cores of "
                                        "this machine")
                        self.cfg[kind] = number
                        changed = True
            elif kind == "addversion":
                name = self.prompt("new Java version name:", "")
                if name is not None and name.strip():
                    self.cfg["versions"].append(default_version(name.strip()))
                    self.runs.append(Run())
                    self.switch_version(len(self.cfg["versions"]) - 1)
                    changed = True
            elif kind == "delversion":
                if len(self.cfg["versions"]) < 2:
                    self.msg = "cannot delete the only Java version"
                elif self.confirm(f"delete Java version '{self.version()['name']}'?"):
                    active = self.cfg["active"]
                    self.cfg["versions"].pop(active)
                    self.runs.pop(active)
                    self.switch_version(min(active, len(self.cfg["versions"]) - 1))
                    changed = True
        elif ch == ord("a"):
            comp = {}
            if self.compiler_dialog(comp, {c["name"] for c in comps}):
                comps.append(comp)
                changed = True
        elif ch == ord("d") and kind == "compiler":
            if self.confirm(f"delete compiler '{comps[idx]['name']}'?"):
                comps.pop(idx)
                changed = True
        elif ch == ord("J") and kind == "compiler" and idx < len(comps) - 1:
            comps[idx], comps[idx + 1] = comps[idx + 1], comps[idx]
            self.ccursor += 1
            changed = True
        elif ch == ord("K") and kind == "compiler" and idx > 0:
            comps[idx], comps[idx - 1] = comps[idx - 1], comps[idx]
            self.ccursor -= 1
            changed = True
        elif ch in (ord("q"), 27):
            self.mode = "main"
        if changed:
            error = save_config(self.cfg)
            if error:
                self.msg = error
        self.ccursor = max(0, min(self.ccursor, len(self.config_rows()) - 1))
        return True

    def main_loop(self):
        """Draw, read a key, dispatch; until the user quits."""
        curses.curs_set(0)
        self.stdscr.timeout(100)
        self.init_colors()
        while True:
            if self.dirty.is_set():
                self.dirty.clear()
            self.draw()
            try:
                ch = self.stdscr.getch()
            except curses.error:
                continue
            if ch == -1 or ch == curses.KEY_RESIZE:
                continue
            self.msg = ""
            if ch == ord("\t"):
                self.switch_version(self.cfg["active"] + 1)
                continue
            if ch == curses.KEY_BTAB:
                self.switch_version(self.cfg["active"] - 1)
                continue
            if self.mode == "main":
                if not self.handle_main(ch):
                    break
            elif self.mode == "diag":
                self.handle_diag(ch)
            elif self.mode == "config":
                self.handle_config(ch)
        self.stop_workers()
        return self.diag_dir


def main():
    args = sys.argv[1:]
    if args and args[0] in ("-h", "--help"):
        print(__doc__)
        return 0
    cfg = load_config()
    if args:
        cfg["tests_root"] = os.path.abspath(args[0])
    if not os.path.isdir(cfg["tests_root"]):
        print(f"tests root not found: {cfg['tests_root']}", file=sys.stderr)
        return 1
    if not os.path.exists(CONFIG_FILE):
        error = save_config(cfg)
        if error:
            print(error, file=sys.stderr)
    if not sys.stdout.isatty():
        print("this is an interactive TUI - run it in a terminal", file=sys.stderr)
        return 1
    diag_dir = curses.wrapper(lambda stdscr: App(stdscr, cfg).main_loop())
    print(f"diagnostics saved under: {diag_dir}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
