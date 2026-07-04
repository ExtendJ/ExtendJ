# Error in uncaught exception checking for lambda expressions

*ExtendJ 8.1.2-15-gd4d25af7 Java SE 8*

ExtendJ incorrectly reports an error about an unhandled exception type for the following test case:

```java
// .result: COMPILE_PASS
public class Test {
  void m() throws Exception {
    doExceptionally(() -> f());
  }

  void f() throws Exception { }

  <E extends Throwable> void doExceptionally(ExceptionalListener<E> fun) throws E {
    fun.apply();
  }
}

interface ExceptionalListener<E extends Throwable> {
  void apply() throws E;
}
```

Expected result: should compile without error

Actual result: ExtendJ prints the following error message:

```
tests/jsr335/lambda/exception_ti_01p/Test.java:4: error: Test.doExceptionally invoked in Test may throw uncaught exception java.lang.Throwable
```

## Comments

### Jesper Öqvist - 2019-03-20

See the comments at the end of  [JLS8 §18.2.5](https://docs.oracle.com/javase/specs/jls/se8/html/jls-18.html#jls-18.2.5):

>The exceptions thrown by a lambda body cannot be determined until i) the parameter types of the lambda are known, and ii) the target type of result expressions in the body is known. (The second requirement is to account for generic method invocations in which, for example, the same type parameter appears in the return type and the throws clause.) Hence, we require both of these, as derived from the target type T, to be proper types.
>
> One consequence is that lambda expressions returned from other lambda expressions cannot generate constraints from their thrown exceptions. These constraints can only be generated from top-level lambda expressions.
