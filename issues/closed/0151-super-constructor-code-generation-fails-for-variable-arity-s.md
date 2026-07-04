# Super constructor code generation fails for variable arity super constructor

**Status:** resolved

**ExtendJ 8.0.1-88-gb3ea8c6**

A super constructor call using a variable arity super constructor fails to generate code. Code generation fails with a NullPointerException for the following test case:

```java
// Test a super constructor call with variable arity arguments.
// .result=COMPILE_PASS
public class Test extends S {
  public Test() {
    super(1, 2);
  }
}

class S {
  S(int... in) {
  }
}
```

This is caused by the `SuperConstructorAccess` not being transformed similarly to a variable arity method invocation before code generation.

## Comments

### Jesper Öqvist - 2016-03-09

Super constructor variable arity transformation

Use variable arity transformation to generate bytecode for a super constructor access.

fixes #151 (bitbucket)


→ <<cset 5b0de737a996>>
