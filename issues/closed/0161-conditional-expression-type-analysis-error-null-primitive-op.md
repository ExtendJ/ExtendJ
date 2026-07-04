# Conditional expression type analysis error (null & primitive operands)

**Status:** resolved

**ExtendJ 8.0.1-102-gd0b096f**

An error in conditional expression type analysis causes the following test case to fail:

```java
// Test conditional expression type analysis.
// .result=COMPILE_PASS
class Test {
  void test(boolean a, float b) {
    f(a ? b : null);
  }

  void f(float f) {
  }

  void f(int i) {
  }
}
```

The above test case fails with this error message:

```
    [junit] tests/type/conditional_expr_07p/Test.java:5: error: no method named f(<NOTYPE>) in Test matches. However, there is a method f(float)
```

## Comments

### Jesper Öqvist - 2016-03-15

Improve conditional expression type analysis

When the second or third operand of a conditional expression is null and the
other is a primitive type, then the type of the conditional expression is the
primitive type.

fixes #161 (bitbucket)


→ <<cset 9128fbddb471>>
