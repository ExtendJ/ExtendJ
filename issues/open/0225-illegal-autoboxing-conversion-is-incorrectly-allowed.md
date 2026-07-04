# Illegal autoboxing conversion is incorrectly allowed

*ExtendJ 8.0.1-222-g9a85c80 Java SE 8*

The following test should fail, but passes:

```java
// Illegal autoboxing conversion.
// .result: COMPILE_FAIL

class Test {
  public static void main(String[] args) {
    int i = f(3);
  }

  static int f(Object i) {
    return (int) i; // Can't convert Object->int.
  }
}
```

Expected result: should fail to compile.

Actual result: compiles without error, and is executable.
