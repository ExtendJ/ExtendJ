# Stack Overflow caused by nested diamond access

*ExtendJ 8.1.0-20-gdf98f7c Java SE 8*

The following test causes a StackOverflowError:

```java
// Test that an argument to a diamond consturctor access can use diamond access.
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  public static void main(String[] args) {
    Set<String> argSet = new HashSet<>(new ArrayList<>());
  }
}
```

Expected result: should pass compilation

Actual result: stack overflow in inferring diamond constructor:

```
    [junit]     at org.extendj.ast.DiamondAccess.potentiallyApplicable(DiamondAccess.java:138)
    [junit]     at org.extendj.ast.DiamondAccess.potentiallyApplicable(DiamondAccess.java:110)
    [junit]     at org.extendj.ast.DiamondAccess.chooseConstructor(DiamondAccess.java:77)
    [junit]     at org.extendj.ast.DiamondAccess.type_compute(DiamondAccess.java:428)
    [junit]     at org.extendj.ast.DiamondAccess.type(DiamondAccess.java:397)
    [junit]     at org.extendj.ast.ClassInstanceExpr.decls_compute(ClassInstanceExpr.java:1051)
    [junit]     at org.extendj.ast.ClassInstanceExpr.decls(ClassInstanceExpr.java:1039)
    [junit]     at org.extendj.ast.ClassInstanceExpr.decl_compute(ClassInstanceExpr.java:1089)
    [junit]     at org.extendj.ast.ClassInstanceExpr.decl(ClassInstanceExpr.java:1077)
    [junit]     at org.extendj.ast.ClassInstanceExpr.Define_targetType(ClassInstanceExpr.java:2033)
    [junit]     at org.extendj.ast.ASTNode.Define_targetType(ASTNode.java:3341)
    [junit]     at org.extendj.ast.Expr.targetType(Expr.java:2219)
    [junit]     at org.extendj.ast.ClassInstanceExpr.Define_assignConvertedType(ClassInstanceExpr.java:2059)
    [junit]     at org.extendj.ast.Expr.assignConvertedType(Expr.java:2170)
    [junit]     at org.extendj.ast.Expr.computeConstraints(Expr.java:249)
    [junit]     at org.extendj.ast.Expr.inferTypeArguments_compute(Expr.java:1313)
    [junit]     at org.extendj.ast.Expr.inferTypeArguments(Expr.java:1298)
    [junit]     at org.extendj.ast.DiamondAccess.potentiallyApplicable(DiamondAccess.java:138)
    [junit]     at org.extendj.ast.DiamondAccess.potentiallyApplicable(DiamondAccess.java:110)
    [junit]     at org.extendj.ast.DiamondAccess.chooseConstructor(DiamondAccess.java:77)
    [junit]     at org.extendj.ast.DiamondAccess.type_compute(DiamondAccess.java:428)
    [junit]     at org.extendj.ast.DiamondAccess.type(DiamondAccess.java:397)
    ...
```

## Comments

### Jesper Öqvist - 2018-01-05

Unify diamond inference with method type inference

This simplifies the diamond implementation by reusing method type inference directly.
This is done by adding a synthetic method access to represent the diamond constructor
access. Type inference is done by reusing method type inference on the synthetic method.

This change removed a lot of very similar code from the diamond inference.  The
change also makes diamond type inference automatically benefit from the updates
to method type inference in Java 8. This fixes an issue with nested diamond
accesses in ExtendJ8.

fixes #266 (bitbucket)


→ <<cset 5af1b54d251e>>

### Jesper Öqvist - 2019-03-14

ExtendJ still fails on a similar but simplified version of the original test case for this issue:


```java

public class Test {
  A<String> a = new A<>(new B<>());
}

class A<T> {
  A(B<T> b) { }
}
class B<T> { }
```


Output:
```
error: can not assign field a of type A<java.lang.String> a value of type A<java.lang.Object>
```
