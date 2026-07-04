# Failure to parse generic constructor bytecode

**Status:** resolved

*ExtendJ 8.1.0-24-g7c0a012 Java SE 5*

ExtendJ fails to parse the bytecode for this class:

```java
package runtime;

// This is used by a test to verify that ExtendJ can load generic constructor signatures.
public class GenericConstructor {
  public <T> GenericConstructor(T t) {
  }
}
```

The failure is triggered by this test that uses the constructor:

```java
// Test that ExtendJ correctly parses generic constructor bytecode.
// .result: COMPILE_PASS
// .classpath: @RUNTIME_CLASSES@
import runtime.GenericConstructor;

public class Test {
  void pass() {
    new GenericConstructor(this);
  }
}
```

Expected result: the program should compile correctly if the class `GenericConstructor` is loaded from bytecode compiled with Javac.

Actual result: when the class `GenericConstructor` is loaded from bytecode, ExtendJ crashes:

```
    [junit] Error while processing tests/generics/constructor_10p/Test.java:7
    [junit] Fatal exception:
    [junit] java.lang.Error: Trying to make a typeDescriptor() of Unknown
    [junit]     at org.extendj.ast.UnknownType.typeDescriptor_compute(UnknownType.java:835)
    [junit]     at org.extendj.ast.UnknownType.typeDescriptor(UnknownType.java:823)
    [junit]     at org.extendj.ast.ConstructorDecl.descName_compute(ConstructorDecl.java:1828)
    [junit]     at org.extendj.ast.ConstructorDecl.descName(ConstructorDecl.java:1805)
    [junit]     at org.extendj.ast.ConstructorDecl.emitInvokeConstructor(ConstructorDecl.java:135)
    [junit]     at org.extendj.ast.ClassInstanceExpr.createBCode(ClassInstanceExpr.java:306)
    [junit]     at org.extendj.ast.ExprStmt.createBCode(ExprStmt.java:51)
    ...
```

## Comments

### Jesper Öqvist - 2018-01-05

Parse generic constructors from bytecode

This fixes an issue in bytecode parsing for constructors. Generic constructors
were previously parsed as non-generic constructors. The type parameters were
incorrectly discarded.

fixes #274 (bitbucket)


→ <<cset cd7effa71375>>
