# Type argument inference does not work for generic methods in a parameterized type

**Status:** resolved

**ExtendJ 7.1.1-368-g0b20c92 Java SE 7**

ExtendJ does not handle method type inference properly for generic methods in parameterized types. For example, the following test case fails to compile:

```java
class Test {
  class A<T> {
    <U> U m() {
      return null;
    }
  }

  {
    A<String> a = new A<String>();
    Integer i = a.m();
  }
}
```

The test case fails to compile with the error:

```
    [junit] [FAIL] runTest[generics/method_08p](tests.extendj.TestJava7)
    [junit] Compilation failed when expected to pass:
    [junit] Errors:
    [junit] tests/generics/method_08p/Test.java:12:
    [junit]   Semantic Error: can not assign variable i of type java.lang.Integer a value of type java.lang.Object
```

This issue seems to be caused from the way BoundTypeAccess nodes are created with a token reference to the type variable they are bound to. Creating a substituted method declaration with those bound type variables then fails to match against the type variables of the newly created substituted generic method declaration.

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
