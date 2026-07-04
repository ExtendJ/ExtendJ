# Constant folding does not work for enum constants

**Status:** resolved

*ExtendJ 8.0.1-157-ge916427 Java SE 8*

Constant expressions that use enum constants cause ExtendJ to crash when ExtendJ tries to fold the constant expression.

The following test case causes ExtendJ to crash:

```java
import static runtime.Test.*;

public class Test {
  public static enum CatType {
    HOUSE, STRAY,
    LASAGNA, TOM,
    HAT, CHESHIRE
  }

  public static final String cat1 = "Cat in the " + CatType.HAT;
}
```

An UnsupportedOperationException is thrown:

```
    [junit] Compilation failed when expected to pass:
    [junit] Fatal exception:
    [junit] java.lang.UnsupportedOperationException: ConstantExpression operation constant not supported for type org.extendj.ast.EnumInstanceE
xpr
    [junit]     at org.extendj.ast.Expr.constant(Expr.java:561)
    [junit]     at org.extendj.ast.VarAccess.constant(VarAccess.java:443)
    [junit]     at org.extendj.ast.AbstractDot.constant(AbstractDot.java:297)
    [junit]     at org.extendj.ast.AddExpr.constant(AddExpr.java:271)
    [junit]     at org.extendj.ast.ConstantValueAttribute.<init>(ConstantValueAttribute.java:34)
    [junit]     at org.extendj.ast.FieldDeclarator.refined_Attributes_FieldDeclarator_attributes(FieldDeclarator.java:433)
    [junit]     at org.extendj.ast.FieldDeclarator.refined_AnnotationsCodegen_FieldDeclarator_attributes(FieldDeclarator.java:443)
    [junit]     at org.extendj.ast.FieldDeclarator.attributes_compute(FieldDeclarator.java:998)
    [junit]     at org.extendj.ast.FieldDeclarator.attributes(FieldDeclarator.java:986)
    [junit]     at org.extendj.ast.ClassDecl.generateClassfile(ClassDecl.java:166)
```

## Comments

### Jesper Öqvist - 2017-04-11

Fix error in enum constant folding

This fixes a crash when attempting to fold a constant expression
including an enum constant.

fixes #191 (bitbucket)


→ <<cset 3361427b2dcd>>
