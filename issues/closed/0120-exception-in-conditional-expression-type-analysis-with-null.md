# Exception in conditional expression type analysis with null operand

**Status:** resolved

**ExtendJ 7.1.1-327-g10a33c4 Java SE 8**

Test case:

```java
// .result=COMPILE_ERR_OUTPUT
public class Test {
  Object f = cond()
      ? new Object()
      : (o==null) ? null : o;
  boolean cond() {
    return System.currentTimeMillis() % 2 == 1;
  }
}
```

Throws exception:

```
    [junit] [FAIL] runTest[jastaddj/conditional_expr_01f](tests.jastaddj.TestJava7)
    [junit] Error output files differ expected:<Errors:
    [junit] [tests/jastaddj/conditional_expr_01f/Test.java:5,10:
    [junit]   Semantic Error: no field named o is accessible
    [junit] tests/jastaddj/conditional_expr_01f/Test.java:5,28:
    [junit]   Semantic Error: no field named o is accessible]> but was:<Errors:
    [junit] [Fatal exception while processing tests/jastaddj/conditional_expr_01f/Test.java:
    [junit] java.lang.Error: Operation not supported for @primitive.null, AST.NullType
    [junit]     at AST.LUBType.addSupertypes(LUBType.java:254)
    [junit]     at AST.LUBType.ST(LUBType.java:212)
    [junit]     at AST.LUBType.EST(LUBType.java:196)
    [junit]     at AST.LUBType.EC(LUBType.java:33)
    [junit]     at AST.LUBType.MEC(LUBType.java:51)
    [junit]     at AST.LUBType.lub_compute(LUBType.java:723)
    [junit]     at AST.LUBType.lub(LUBType.java:706)
    [junit]     at AST.LUBType.supertypeClassDecl(LUBType.java:827)
    [junit]     at AST.ClassDecl.subtype(ClassDecl.java:2240)
    [junit]     at AST.ClassDecl.instanceOf_compute(ClassDecl.java:1850)
    [junit]     at AST.ClassDecl.instanceOf(ClassDecl.java:1838)
    [junit]     at AST.ReferenceType.wideningConversionTo(ReferenceType.java:429)
    [junit]     at AST.TypeDecl.refined_TypeConversion_TypeDecl_assignConversionTo_TypeDecl_Expr(TypeDecl.java:1871)
    [junit]     at AST.TypeDecl.assignConversionTo(TypeDecl.java:3639)
    [junit]     at AST.ConditionalExpr.refined_TypeAnalysis_ConditionalExpr_type(ConditionalExpr.java:405)
    [junit]     at AST.ConditionalExpr.refined_AutoBoxing_ConditionalExpr_type(ConditionalExpr.java:429)
    [junit]     at AST.ConditionalExpr.type_compute(ConditionalExpr.java:611)
    [junit]     at AST.ConditionalExpr.type(ConditionalExpr.java:598)
    [junit]     at AST.FieldDeclaration.typeCheck(FieldDeclaration.java:203)
    [junit]     at AST.ASTNode.collectErrors(ASTNode.java:1194)
    [junit]     at AST.ASTNode.collectErrors(ASTNode.java:1202)
    [junit]     at AST.ASTNode.collectErrors(ASTNode.java:1202)
    [junit]     at AST.ClassDecl.collectErrors(ClassDecl.java:53)
    [junit]     at AST.ASTNode.collectErrors(ASTNode.java:1202)
    [junit]     at AST.ASTNode.collectErrors(ASTNode.java:1202)
    [junit]     at AST.Frontend.processCompilationUnit(Frontend.java:216)
    [junit]     at org.jastadd.extendj.JavaCompiler.processCompilationUnit(JavaCompiler.java:92)
    [junit]     at AST.Frontend.run(Frontend.java:136)
    [junit]     at org.jastadd.extendj.JavaCompiler.run(JavaCompiler.java:86)
    [junit]     at org.jastadd.extendj.JavaCompiler.main(JavaCompiler.java:34)]>
    [junit] Test completed: 6 runs, 1 failures, 0 errors
    [junit] Test tests.jastaddj.TestJava7 FAILED
```

## Comments

### Jesper Öqvist - 2015-07-13

Added regression test for null in conditional expr

see #120 (bitbucket)


→ <<cset 1b6092ddc1d3>>

### Jesper Öqvist - 2015-07-13

Fix exception from null type in conditional expr

fixes #120 (bitbucket)


→ <<cset f162ae4717a6>>
