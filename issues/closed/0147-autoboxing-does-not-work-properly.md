# Autoboxing does not work properly

**Status:** resolved

**ExtendJ 8.0.1-75-g6738dd8**

ExtendJ is a bit too strict about which types it will allow autoboxing conversions for. A test case illustrating the problem:

```java
// Test autoboxing.
// .result=COMPILE_PASS

class Test {
  public static void main(String[] args) {
    int i = f(3); // Autoboxing conversion int->Integer.
  }

  static int f(Object i) {
    return (int) i; // Autoboxing conversion Integer->int.
  }
}
```

This passes with Javac, but gives the following errors with ExtendJ:

```
    [junit] [FAIL] runTest[type/autoboxing_02p](tests.javac.TestJava8)
    [junit] Compilation failed when expected to pass:
    [junit] tests/type/autoboxing_02p/Test.java:10: error: incompatible types: Object cannot be converted to int
    [junit]     return i; // Autoboxing conversion Integer->int.
```

## Comments

### Jesper Öqvist - 2016-03-01

Fix boxing conversions type checking and code gen

Fixed problem in the type analysis for autoboxing conversions. Fixed problem in
bytecode generation for autoboxing conversions.

fixes #147 (bitbucket)


→ <<cset 270d5ecd8969>>
