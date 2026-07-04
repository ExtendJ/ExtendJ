# Incorrect binary numeric promotion in conditional expression

**Status:** resolved

**ExtendJ 8.0.1-98-ge82120a**

There is an error in conditional expression type analysis causing an incorrect type to be computed when one operand has type `byte` and the other has type `Byte`. The test `type/conditional_expr_02p` in the regression test suite exposes this error:

```java
class Test {
  byte test(byte a, Byte b, boolean c) {
    return c ? a : b;
  }
}
```

ExtendJ reports the following error for the above code:

```
    [junit] tests/type/conditional_expr_02p/Test.java:6: error: return value must be an instance of byte which int is not
```

The error is caused by using a binary numeric promotion too early. Binary numeric promotion should not occur when one operand is just the boxed type of the other operand type.

## Comments

### Jesper Öqvist - 2016-03-13

Improve conditional expression type analysis

fixes #157 (bitbucket)


→ <<cset 1413da229c78>>
