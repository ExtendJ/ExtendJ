# Incorrect method binding due to variable arity

**Status:** resolved

**ExtendJ 8.0.1-54-g387b459 Java SE 8**

The most specific method computation fails for variable arity methods as in the test below:

```java
// A variable arity method is more specific when passed an array type.
// .result=EXEC_PASS
public class Test {
  static boolean pass(Object p) {
    return false;
  }
  static boolean pass(Object... p) {
    return true;
  }
  public static void main(String[] args) {
      if (!pass(new String[0])) {
      throw new Error("Wrong method called.");
    }
  }
}
```

ExtendJ fails to compute the method binding for the call to `pass(new String[0])`. It should bind to the variable arity method, but instead gives this error:

```
    [junit] tests/method/varargs_01p/Test.java:11: error: several most specific methods for pass(new String[0])
    [junit]     pass(Object[]) in Test
    [junit]     pass(java.lang.Object) in Test
```

## Comments

### Jesper Öqvist - 2016-02-23

Fix variable arity handling in method selection

fixes #142 (bitbucket)


→ <<cset 542aea5ba221>>
