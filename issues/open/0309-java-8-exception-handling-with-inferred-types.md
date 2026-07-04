# Java 8 exception handling with inferred types

*ExtendJ 8.1.2-15-gd4d25af7 Java SE 8*

The behavior of exception checking for generic methods has changed slightly after Java 7 and ExtendJ does not have the right behavior for Java 8.

The following test case should not cause an error when compiling with Java 8, but fails to compile with Java 7:

```java
public class Test {
  void m() {
    f();
  }

  <E extends Exception> void f() throws E { }
}
```

Expected result: the test should compile with ExtendJ Java 8

Actual result: the test fails to compile with the following error message:

```
error: Test.f invoked in Test may throw uncaught exception java.lang.Exception
```

## Comments

### Jesper Öqvist - 2019-03-20

Relevant part of [JLS8 §18.4](https://docs.oracle.com/javase/specs/jls/se8/html/jls-18.html#jls-18.4):

>Otherwise, if the bound set contains throws αi, and the proper upper bounds of αi are, at most, Exception, Throwable, and Object, then Ti = RuntimeException.
