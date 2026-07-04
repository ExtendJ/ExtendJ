# Wildcard type argument is not compatible with type variable upper bound

**Status:** resolved

*ExtendJ 8.0.1-154-gaf5cf06 Java SE 8*

When a generic type is parameterized with a wildcard type argument, the type argument should be compatible with the upper bound of the type variable.

In the following test case, the `I` type uses upper bound `Thing` for type variable `T`, thus `I<?>` should be compatible with `I<? extends Thing>`, but it is not:

```java
// Test method invocation type conversion.
// .result=COMPILE_PASS

class Thing {}
interface I<T extends Thing> {}

class Test {

  static void foo(I<? extends Thing> i) { }

  void m(I<?> i) {
    foo(i);  // Should work fine: the upper bound of T is Thing.
  }
}
```

ExtendJ reports the following error:

```
tests/generics/method_11p/Test.java:12: error: no method named foo(I<wildcards.?>) in Test matches. However, there is a method foo(I<wildcards.? extends Thing>)
```

Javac does not report an error for this test case.

## Comments

### Jesper Öqvist - 2017-04-10

Improve handling of wildcard in parameterizations

Wildcard type arguments were not substituted for their upper bound
when testing if a parameterization was contained in another
parameterization.

fixes #189 (bitbucket)


→ <<cset e9164274b15c>>
