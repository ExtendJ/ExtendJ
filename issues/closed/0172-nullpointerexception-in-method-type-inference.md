# NullPointerException in method type inference

**Status:** resolved

**ExtendJ 8.0.1-121-gc55b02b Java SE 8**

The following simple test case causes a NullPointerException:

```java
public class Test {
    private static final int i = 0;

    public <T extends Test> T a() {
        return b();
    }

    <T extends Test> T b() {
        return null;
    }

}
```

Here is part of the stack trace:

```
java.lang.NullPointerException
        at org.extendj.ast.BodyDecl.hostType(BodyDecl.java:730)
        at org.extendj.ast.FieldDecl.isPublic(FieldDecl.java:570)
        at org.extendj.ast.TypeVariable.toInterface_compute(TypeVariable.java:591)
        at org.extendj.ast.TypeVariable.toInterface(TypeVariable.java:567)
        at org.extendj.ast.GLBTypeFactory.addInterfaces(GLBTypeFactory.java:76)
        at org.extendj.ast.GLBTypeFactory.glb(GLBTypeFactory.java:40)
        at org.extendj.ast.Constraints.resolveSubtypeConstraints(Constraints.java:186)
        at org.extendj.ast.Expr.computeConstraints(Expr.java:257)
        at org.extendj.ast.Expr.inferTypeArguments_compute(Expr.java:1243)
        at org.extendj.ast.Expr.inferTypeArguments(Expr.java:1228)
        at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:1687)
        at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:152)
        at org.extendj.ast.MethodAccess.maxSpecific(MethodAccess.java:570)
        at org.extendj.ast.MethodAccess.decls_compute(MethodAccess.java:1141)
        at org.extendj.ast.MethodAccess.decls(MethodAccess.java:1129)
```

## Comments

### Jesper Öqvist - 2016-07-12

Added the above test to the regression test suite as test case `generics/inference_06p`.

### Jesper Öqvist - 2016-07-13

This bug is caused by first copying some `BodyDecl` nodes and then evaluating `isPublic()` on the copies in `GLBTypeFactory.jadd`. The fix is to simply evaluate `isPublic()` before the copy.

### Jesper Öqvist - 2016-07-13

Avoid attribute evaluation in unrooted subtrees

The isPublic() attribute was evaluated on fresh copies of BodyDecl nodes
leading to NullPointerExceptions in some cases.

fixes #172 (bitbucket)


→ <<cset 06a46a558d57>>
