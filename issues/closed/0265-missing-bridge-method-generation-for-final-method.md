# Missing bridge method generation for final method

**Status:** resolved

*ExtendJ 8.1.0-19-g5c20689 Java SE 8*

ExtendJ fails to generate a necessary bridge method for the method `X.x()` in this test:

```java
public class An5 {
  public static void main(String[] args) {
    System.out.println(foo(new X()));
  }

  static Object foo(S s) {
    return s.x();
  }
}

class S {
  Object x() {
    return null;
  }
}
class X extends S {
  @Override
  final X x() {
    return this;
  }
}
```

Expected result: the compiled program should print `ok`

Actual result: the program prints `null`

## Comments

### Jesper Öqvist - 2018-01-04

Generate bridge methods for final methods

Bridge methods should be generated, if needed, for final methods.

fixes #265 (bitbucket)


→ <<cset debd2c74c7f1>>
