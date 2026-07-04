# Flaky compilation failures with Java 7 TWR

**Status:** resolved

*ExtendJ 8.1.0-7-g5bb53b0 Java SE 7*

The following code sometimes compiles, sometimes fails to compile with ExtendJ:

```java
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class An1 {
  public static void pass(Path path) throws IOException {
    try (ReadableByteChannel chan = Files.newByteChannel(path)) {
    }
  }
}
```

Expected result: should always compile without errors.

Actual result: sometimes fails with an incorrect error about unhandled exceptions:

```
16:58:27 ~/git/extendj $ java -jar extendj.jar -d tmp test/An1.java
16:58:28 ~/git/extendj $ java -jar extendj.jar -d tmp test/An1.java
16:58:30 ~/git/extendj $ java -jar extendj.jar -d tmp test/An1.java
test/An1.java:11: error: automatic closing of resource chan may raise the uncaught exception java.lang.Exception; it must be caught or declared as being thrown
16:58:31 ~/git/extendj $ java -jar extendj.jar -d tmp test/An1.java
16:58:37 ~/git/extendj $ java -jar extendj.jar -d tmp test/An1.java
16:58:38 ~/git/extendj $ java -jar extendj.jar -d tmp test/An1.java
16:58:39 ~/git/extendj $ java -jar extendj.jar -d tmp test/An1.java
test/An1.java:8: error: automatic closing of resource chan may raise the uncaught exception java.lang.Exception; it must be caught or declared as being thrown
```

## Comments

### Jesper Öqvist - 2018-01-01

Failure rate becomes higher if some unrelated code is added:

```java
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class An1 {
  void a1() {}
  void a2() {}
  void a3() {}
  void a4() {}
  void a5() {}
  void a6() {}
  void a7() {}
  void a8() {}
  void a9() {}
  void a10() {}
  void a12() {}
  void a13() {}
  void a14() {}
  void a15() {}
  void a16() {}
  void a17() {}
  void a18() {}
  void a19() {}
  public static void pass(Path path) throws IOException {
    try (ReadableByteChannel chan = Files.newByteChannel(path)) {
    }
  }
}
```

### Jesper Öqvist - 2018-01-01

The `TryWithResources.lookupClose()` attribute is not exact. The result can depend on the order of method declarations in the `memberMethods(String)` attribute. Here is the current implementation:

```java
  /**
   * Lookup the close method declaration for the resource which is being used.
   */
  syn MethodDecl TryWithResources.lookupClose(ResourceDeclaration resource) {
    TypeDecl resourceType = resource.getTypeAccess().type();
    for (MethodDecl method : (Collection<MethodDecl>) resourceType.memberMethods("close")) {
      if (method.getNumParameter() == 0) {
        return method;
      }
    }
    // We should not throw a runtime exception here. If there is no close
    // method it likely means that the resource type is not a subtype of
    // java.lang.AutoCloseable and type checking will report this error.
    return null;
  }
```

We need more precise lookup for the `close()` method. To do this, it should be possible to reuse the standard method lookup implementation by adding an NTA to represent the implicit `close()` method access.

### Jesper Öqvist - 2018-01-01

Using an NTA method access to lookup the `close()` method does not help. There seems to be some nondeterminism in the handling of interface methods that makes the lookup for the `close()` method nondeterministic.

### Jesper Öqvist - 2018-01-01

The type of `ReadableByteChannel` is interesting:

* java.nio.channels.ReadableByteChannel extends interface java.nio.channels.Channel
* java.nio.channels.Channel extends both java.io.Closeable and java.lang.AutoCloseable

The type `java.nio.channels.Channel` declares a method `close()` that throws `IOException`, so that should be the declaration for the `close()` access. However, I wrote an API test that demonstrates that the method acces is not always bound to that exact method:

```java
// Test method binding for implicit close() method access in try-with-resources.
// https://bitbucket.org/extendj/extendj/issues/260/flaky-compilation-failures-with-java-7-twr
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.CompilationUnit;
import org.extendj.ast.FileClassSource;
import org.extendj.ast.GenericInterfaceDecl;
import org.extendj.ast.JavaParser;
import org.extendj.ast.Program;
import org.extendj.ast.ParInterfaceDecl;
import org.extendj.ast.RawInterfaceDecl;
import org.extendj.ast.SourceFolderPath;
import org.extendj.ast.TypeVariable;
import org.extendj.ast.TypeDecl;
import org.extendj.ast.TryWithResources;
import org.extendj.ast.MethodDecl;
import org.extendj.ast.Access;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import static runtime.Test.testSame;
import static runtime.Test.fail;

public class Test {
  public static void main(String[] args) {
    // Repeat a few times to catch flaky error.
    for (int i = 0; i < 4; ++i) {
      String code =
        "import java.nio.channels.ReadableByteChannel;"
        + "import java.io.IOException;"
        + "class Foo {"
        + "  public void m(ReadableByteChannel in) throws IOException {"
        + "    try (ReadableByteChannel res = in) {"
        + "    }"
        + "  }"
        + "}";
      CompilationUnit cu = parseCompilationUnit(code);
      MethodDecl method = (MethodDecl) cu.getTypeDecl(0).getBodyDecl(0);
      TryWithResources twr = (TryWithResources) method.getBlock().getStmt(0);
      MethodDecl closeMethod = twr.getResource(0).closeAccess().decl();
      if (!closeMethod.hostType().fullName().equals("java.nio.channels.Channel")) {
        System.err.format("(%d) Unexpected host type for close(): %s%n",
            i, closeMethod.hostType().fullName());
        return;
      }
    }
  }

  /**
   * Parse a compilation unit.
   */
  static CompilationUnit parseCompilationUnit(String code) {
    try {
      Program program = new Program();
      program.initBytecodeReader(Program.defaultBytecodeReader());
      JavaParser sourceParser = Program.defaultJavaParser();
      program.initJavaParser(sourceParser);
      CompilationUnit unit = sourceParser.parse(
          new ByteArrayInputStream(code.getBytes("UTF-8")), "<no path>");
      program.addCompilationUnit(unit);
      unit = program.getCompilationUnit(0);
      unit.setClassSource(new FileClassSource(new SourceFolderPath("<no source>"), "<no path>"));
      unit.setFromSource(true);
      return unit;
    } catch (Exception e) {
      e.printStackTrace();
      fail("failed to parse test code");
    }
    // Failed.
    return null;
  }
}
```

### Jesper Öqvist - 2018-01-01

Part of the problem is in the attribute `InterfaceDecl.methodsSignatureMap()`:

```java
  refine MemberMethods eq InterfaceDecl.methodsSignatureMap() {
    Map<String, SimpleSet<MethodDecl>> localMap = localMethodsSignatureMap();
    Map<String, SimpleSet<MethodDecl>> map = new HashMap<String, SimpleSet<MethodDecl>>(localMap);
    for (Iterator<MethodDecl> iter = interfacesMethodsIterator(); iter.hasNext(); ) {
      MethodDecl m = iter.next();
      if (m.accessibleFrom(this) && !localMap.containsKey(m.signature())) {
        if (!(m instanceof MethodDeclSubstituted)
            || !localMap.containsKey(m.sourceMethodDecl().signature())) {
          putSimpleSetElement(map, m.signature(), m);
        }
      }
    }
    for (Iterator iter = typeObject().methodsIterator(); iter.hasNext(); ) {
      MethodDecl m = (MethodDecl) iter.next();
      if (m.isPublic() && !map.containsKey(m.signature())) {
        putSimpleSetElement(map, m.signature(), m);
      }
    }
    return map;
  }
```

The `!localMap.containsKey(m.sourceMethodDecl().signature())` condition causes an order dependence for the result. The order of method declarations in `interfacesMethodsIterator()` is arbitrary.

### Jesper Öqvist - 2018-01-02

After more debugging, it seems like the problematic nondeterminism is actually caused by the methods:

* `MethodAccess.maxSpecific(Collection<MethodDecl>)` - uses hash sets (via `SimpleSet`) to filter applicable methods.
* `TypeDecl.methodsIterator()` - uses `methodsSignatureMap().values()`, which has `SimpleSet` values.

The `SimpleSet` datastructures can be implemented by HashSet, which has nondeterministic order. Where order matters, we should use ArrayList or LinkedHashSet.

### Jesper Öqvist - 2018-01-02

Make SimpleSet values ordered by insertion order

This makes the order of elements in the SimpleSet implementations
deterministic.

fixes #260 (bitbucket)


→ <<cset 8b61f60dfde3>>
