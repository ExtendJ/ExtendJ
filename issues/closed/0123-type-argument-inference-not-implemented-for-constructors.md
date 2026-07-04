# Type argument inference not implemented for constructors

**Status:** resolved

**ExtendJ 7.1.1-368-g0b20c92 Java SE 7**

Java 6 added type inference for method and constructor type arguments. Type argument inference seems to only be implemented in ExtendJ for method calls.

Below is a test case illustrating that type argument inference does not work for constructor calls in ExtendJ.

```java
class Test {
  class A {
    <T extends Throwable> A(T t) throws T {
      throw t;
    }
  }

  {
    new A(new Error("unchecked")); // In this invocation T is an unchecked exception type.
  }
}
```

The test case should not fail to compile, but ExtendJ gives the following error:

```
    [junit] [FAIL] runTest[generics/constructor_04p](tests.extendj.TestJava7)
    [junit] Compilation failed when expected to pass:
    [junit] Errors:
    [junit] tests/generics/constructor_04p/Test.java:13:
    [junit]   Semantic Error: new A(new Error("unchecked")) may throw uncaught exception T; it must be caught or declared as being thrown
```

The error message shows that the type of T was not inferred to `Error` as it should have been.

## Comments

### Jesper Öqvist - 2015-09-09

Type argument inference bug fixes

Added type argument inference for constructors.

Fixed type inference for generic methods declared in parameterized types.

GenericMethodDecl is no longer created by substituedBodyDecl, because this
causes incorrect type bindings for any BoundTypeAccess in the substituted
generic declaration.

fixes #123 (bitbucket)
fixes #124 (bitbucket)


→ <<cset b09f45dff33e>>
