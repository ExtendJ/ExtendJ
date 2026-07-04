# Lambda with return statement interferes with definite assignment analysis

**Status:** resolved

*ExtendJ 8.1.0-54-g027b9c7 Java SE 8*

```java
// Lambda with return statement should not interfere with
// definite assignment analysis.
// .result: COMPILE_PASS
public class Test {
  private final Runnable nothing;

  public Test() {
    nothing = () -> { return; };
  }
}
```

Expected result: should compile without errors.

Actual result: ExtendJ reports an error:

```
    [junit] [FAIL] runTest[jsr335/lambda/da_01p](tests.extendj.TestJava7)
    [junit] Compilation failed when expected to pass:
    [junit] tests/jsr335/lambda/da_01p/Test.java:5: error: blank final instance variable nothing in Test is not definitely assigned after Test()
```

## Comments

### Jesper Öqvist - 2018-01-11

Do not collect branches inside lambda expressions

Branches inside lambda expressions should not contribute to the branches in the
enclosing block declaration.

This fixes an error where branches in lambdas incorrectly interfered with
definite assignment analysis.

fixes #292 (bitbucket)


→ <<cset 06d4c1bd3d31>>
