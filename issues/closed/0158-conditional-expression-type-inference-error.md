# Conditional expression type inference error

**Status:** resolved

**ExtendJ 8.0.1-100-g0c21e83**

There is an error in the type analysis for conditional expressions when using type inference. A test case showing the error:

```java
// Test conditional expression type analysis.
// .result=COMPILE_PASS
public class Test {
  void test(boolean a, C1 b, C2 c) {
    f(a ? b : c);
  }

  <R> void f(S<R> x) {
  }
}

class S<T> {
}

class C1 extends S<Integer> {
}

class C2 extends S<Integer> {
}
```

ExtendJ gives the following error for the above test case:

```
    [junit] Encountered error while processing tests/generics/conditional_expr_04p/Test.java
    [junit] Fatal exception:
    [junit] java.lang.Error: Operation not supported for wildcards.& C1& C2, org.extendj.ast.LUBType
    [junit]     at org.extendj.ast.Constraints.directSupertypes(Constraints.java:231)
    [junit]     at org.extendj.ast.Constraints.addParameterizedSupertypes(Constraints.java:252)
    [junit]     at org.extendj.ast.Constraints.parameterizedSupertypes(Constraints.java:239)
    [junit]     at org.extendj.ast.Constraints.convertibleTo(Constraints.java:340)
    [junit]     at org.extendj.ast.Expr.computeConstraints(Expr.java:232)
    [junit]     at org.extendj.ast.Expr.inferTypeArguments_compute(Expr.java:1269)
    [junit]     at org.extendj.ast.Expr.inferTypeArguments(Expr.java:1258)
    [junit]     at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:1751)
    [junit]     at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:164)
    ...
```

The error happens only when type inference is used on the result of the conditional expression. The following test case does not fail as the one above:

```java
// Test conditional expression type analysis.
// .result=COMPILE_PASS
public class Test {
  S<?> test(boolean a, C1 b, C2 c) {
    return a ? b : c;
  }
}

class S<T> {
}

class C1 extends S<Integer> {
}

class C2 extends S<Integer> {
}
```

## Comments

### Jesper Öqvist - 2016-03-14

Fix type inference error for conditional expr

fixes #158 (bitbucket)


→ <<cset f92bc195e45d>>
