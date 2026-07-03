# ExtendJ Regression Tests

This project contains regression tests for the ExtendJ Java compiler. The tests
can be verified against javac.

## Quick Start

Note: the tests can be run with either Apache Ant or Gradle. If you use Gradle,
just replace `ant` with `gradle` in the commands below.  Note that Gradle can
only be used with Java 7 or higher. For Java 5 or 6 you must use Ant.

1. Copy `extendj.jar` into the regression-tests root directory (where `build.xml` is).
2. Run tests from the command line:
    * run all default tests (the TestShouldPass suite):
        `ant`
    * or, run some specific test(s):
        `ant -Dtest=ast/flush01`
3. Run tests from Eclipse:
    * Select a test suite, e.g., `src/tests/extendj/TestJava7.java`
    * Select `Popup-menu->Run As->JUnit Test`
4. Add a new test case:
    * A test case is a directory with `.java .jrag .ast .description`, etc. files
    * Add a new test directory in the `tests` directory hierarchy.
    * To be recognized as a test case it *must* include either a `.java` or a `.description` file.
5. Add your own test suite:
    * Copy an existing test suite.
    * Change it to include and exclude parts of the full test suite.

## Quick Compile Diff Testing Tool

`DiffRunner.java` is a standalone tool for quickly comparing two ExtendJ
versions. It compiles every regression test with two different builds
of ExtendJ in a single JVM and reports the tests where the builds disagree on
whether compilation succeeds.

    javac DiffRunner.java
    java DiffRunner <[label=]classpath> <[label=]classpath> [testsRoot]

For example, comparing a baseline jar against the current build:

    java DiffRunner baseline.jar ../java8/extendj.jar

Note that DiffRunner only diffs compile pass/fail. It does not check expected
results, error output, or execution. It is mainly useful for running quick
regression testing during development and does not replace the JUnit testing framework.

## Test Organization

Tests are organized into subdirectories according to which feature they are
intended to test. The test directory names end with a number to accomodate
multiple tests that test the same thing.

If a test tests several different features it should probably be divided into
several tests that test only one distinct feature.

Each test directory *must* contain either the file `Test.java` or
`Test.properties`. This is what identifies the directory as being a test
directory.

Each directory can have a `description` file, which is processed by
[Markdown](http://daringfireball.net/projects/markdown/syntax)
and included in the generated file `index.html`. Generate the index file
by the script `gendoc.sh`.

## Test Suites

Test suites are located in `src/tests/jastadd2`. Each test suite is a regular
parameterized JUnit test. All test suites are identical except for their
properties which are setup in a static initializer.

This an example of how to initialize the test properties so that only tree copy
tests are run with JastAdd2, with the global tab indentation option:

    static {
      properties.put("jastadd3", "false");
      properties.put("jastadd.jar", "jastadd2.jar");
      properties.put("options", "indent=tab");
      properties.include("copy");
    }

Single tests or test suites can be included using `properties.include()`, and
tests can also be excluded by using `properties.exclude()`. The test suite
properties should not be confused with the individual test properties!  Test
suite properties are used to configure JastAdd2 for the entire test suite and
include/exclude individual tests. Test properties on the other hand configure a
single test.


## Test Folder Structure

The test folder must contain a file named `Test.java` or `Test.properties`.
Additional test metadata files:

* `out.expected` - expected standard output.
* `err.expected` - expected standard error output.
* `extendj.err.expected` - expected standard error output when compiling with ExtendJ.

## Test Properties

Each test can have an optional `Test.properties` file that defines options for
that particular test. These options are available:

* `result` - defines the expected test result
* `classpath` - defines the classpath to be used when compiling and executing
  the test code. You can use the variable `@TMP_DIR@` to reference the
temporary directory, or `@TEST_DIR@` to reference the test source directory.
The `@RUNTIME_CLASSES@` variable is used to link with the runtime test framework.
* `options` - defines sets of options to be added to the JastAdd configuration
  just for this test. See below for details.
* `sources` - a comma separated list of source files to be passed to JastAdd.
  This option is useful when the particular order of source files on the
JastAdd command line needs to be tested.

JastAdd flags can be added using the `options` test property. Flags are
separated by whitespace and are given without the '--' prefix. The test can
also be tested with different flags that are separated with the operator `|`,
for example,

    options = tracing=compute | tracing=compute componentCheck


## Test Running

The following operations are performed for each test:

1. JastAdd generates code for the test using all `.ast`, `.jrag` and `.jadd`
files in the test directory. Generated code is output in the `tmp` directory
and everything printed to `stderr` by JastAdd is dumped to the `jastadd.err`
file.
2. The generated Java code is compiled, along with all `.java` files in the
test directory.
3. The Test class is run with `stdout` redirected to the `out` file in the `tmp`
directory, and `stderr` redirected to the `err` file in the `tmp` directory.
4. If the `err` file is non-empty: if the current compiler is ExtendJ,
the error output is compared to the test file `extendj.err.expected`.
Otherwise, it is compared to `err.expected`.
5. If the `err` file is empty, then the `out` file is
compared to the file `out.expected`.

Each test can be configured to halt at any of the above steps by using the
`result` option in the `Test.properties` file:

* `COMPILE_FAIL` = The generated code fails to compile.
  If the test includes a file `extendj.err.expected`, then ExtendJ's error
  output must match that file.
* `COMPILE_PASS` = The generated code compiles without error
* `COMPILE_OUTPUT` = The compiler generated the expected output
* `COMPILE_WARNING` = Compilation passed but the compiler printed some warnings (on stderr).
* `COMPILE_ERR_OUTPUT` = The compiler generated the expected error message
* `EXEC_FAIL`    = The compiled code returns with non-zero exit status when
  executed and prints nothing on standard error
* `EXEC_PASS`    = The compiled code returns with zero exit status when
  executed and prints nothing on standard error, and the output matches the
  `out.expected` file (or is empty if `out.expected` does not exist in
  the test directory).

By default the test will have the expected result `EXEC_PASS`.
