# Failure to find most specific method when comparing varargs methods in Java 5, 6, 7

**Status:** resolved

*ExtendJ 8.1.0-3-g1c6e3bd Java SE 5*

The following test fails to compile:

```java
public class Test {
  public static void main(String[] args) {
    reportError("nothing marks the spot", new Throwable());
    reportError("%s marks the spot", new Throwable(), "x");
    reportError("%s marks the spot", new Test());
    reportError("%s marks the %s", "x", new Throwable());
  }

  static void reportError(String message, Throwable excp, Object... args) {
    System.out.format("Exception (%s): %s%n",
        excp.getClass().getName(),
        String.format(message, args));
  }

  static void reportError(String message, Object... args) {
    System.out.println("Error: " + String.format(message, args));
  }

  public String toString() {
    return "TEST";
  }
}
```

Expected result: compilation should pass.

Actual result: ExtendJ5, ExtendJ6, and ExtendJ7 fail to find the most specific method for the first two calls to `reportError`:

```
    [junit] tests/method/varargs_03p/Test.java:3: error: several most specific methods for reportError("nothing marks the spot", new Throwable())
    [junit]     reportError(java.lang.String, Object[]) in Test
    [junit]     reportError(java.lang.String, java.lang.Throwable, Object[]) in Test
    [junit]
    [junit] tests/method/varargs_03p/Test.java:4: error: several most specific methods for reportError("%s marks the spot", new Throwable(), "x")
    [junit]     reportError(java.lang.String, Object[]) in Test
    [junit]     reportError(java.lang.String, java.lang.Throwable, Object[]) in Test
```

## Comments

### Jesper Öqvist - 2018-01-01

Improve most specific method/constructor analysis

Improved handling of variable arity methods/constructors when finding most
specific method/constructor.

fixes #258 (bitbucket)


→ <<cset e29685049fbb>>
