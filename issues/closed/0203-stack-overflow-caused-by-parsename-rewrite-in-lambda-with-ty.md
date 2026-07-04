# Stack Overflow caused by ParseName rewrite in lambda with type inference

**Status:** resolved

*ExtendJ 8.0.1-174-g53debda Java SE 8*

The following test case compiles fine with javac but causes a StackOverflowError in ExtendJ:

```java
import java.util.function.Function;

class Token {
  public final String string;
  public Token(String token) {
    this.string = token;
  }
}

interface Lambda3 {
  <T> String transform(T in, Function<T, String> fun);

  String replaceWords(String str, Function<String, String> fun);

  default void m(String string, String prefix) {
    transform(new Token(string), token -> prefix + token.string.toString());
  }
}
```

The stack overflow is caused by a rewrite being triggered during type inference for the `transform` method access inside the `m` declaration.

## Comments

### Jesper Öqvist - 2017-05-11

Regression test for this issue: `jsr335/lambda/type_inf_04p`

### Jesper Öqvist - 2017-11-24

Partial stack trace for this issue:

```
    [junit] java.lang.StackOverflowError
    [junit]     at org.extendj.ast.MethodAccess.decl_compute(MethodAccess.java:1231)
    [junit]     at org.extendj.ast.MethodAccess.decl(MethodAccess.java:1219)
    [junit]     at org.extendj.ast.MethodAccess.Define_targetType(MethodAccess.java:2343)
    [junit]     at org.extendj.ast.ASTNode.Define_targetType(ASTNode.java:3366)
    [junit]     at org.extendj.ast.Expr.targetType(Expr.java:2181)
    [junit]     at org.extendj.ast.LambdaExpr.targetInterface_compute(LambdaExpr.java:1058)
    [junit]     at org.extendj.ast.LambdaExpr.targetInterface(LambdaExpr.java:1046)
    [junit]     at org.extendj.ast.InferredLambdaParameters.Define_inferredType(InferredLambdaParameters.java:498)
    [junit]     at org.extendj.ast.ASTNode.Define_inferredType(ASTNode.java:4166)
    [junit]     at org.extendj.ast.InferredParameterDeclaration.inferredType(InferredParameterDeclaration.java:769)
    [junit]     at org.extendj.ast.InferredParameterDeclaration.type(InferredParameterDeclaration.java:617)
    [junit]     at org.extendj.ast.NamePart$VarName.lookupType(NamePart.java:233)
    [junit]     at org.extendj.ast.ParseName.resolveAmbiguousName(ParseName.java:142)
    [junit]     at org.extendj.ast.ParseName.rewriteRule0(ParseName.java:387)
    [junit]     at org.extendj.ast.ParseName.rewriteTo(ParseName.java:355)
    [junit]     at org.extendj.ast.ParseName.rewrittenNode(ParseName.java:454)
    [junit]     at org.extendj.ast.ASTNode.getChild(ASTNode.java:777)
    [junit]     at org.extendj.ast.Dot.treeCopy(Dot.java:268)
    [junit]     at org.extendj.ast.Dot.treeCopy(Dot.java:41)
    [junit]     at org.extendj.ast.ExprLambdaBody.treeCopy(ExprLambdaBody.java:184)
    [junit]     at org.extendj.ast.ExprLambdaBody.treeCopy(ExprLambdaBody.java:35)
    [junit]     at org.extendj.ast.LambdaExpr.treeCopy(LambdaExpr.java:229)
    [junit]     at org.extendj.ast.LambdaExpr.treeCopy(LambdaExpr.java:35)
    [junit]     at org.extendj.ast.List.treeCopy(List.java:202)
    [junit]     at org.extendj.ast.MethodAccess.boundAccess_compute(MethodAccess.java:1970)
    [junit]     at org.extendj.ast.MethodAccess.boundAccess(MethodAccess.java:1955)
    [junit]     at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:1743)
    [junit]     at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:576)
    [junit]     at org.extendj.ast.MethodAccess.maxSpecific(MethodAccess.java:665)
    [junit]     at org.extendj.ast.MethodAccess.decls_compute(MethodAccess.java:1186)
    [junit]     at org.extendj.ast.MethodAccess.decls(MethodAccess.java:1174)
    [junit]     at org.extendj.ast.MethodAccess.decl_compute(MethodAccess.java:1231)
    [junit]     at org.extendj.ast.MethodAccess.decl(MethodAccess.java:1219)
```

The StackOverflow would not occur if the treeCopy used to create a bound method access (`MethodAccess.boundAccess()`) did not try to evaluate rewrites.

### Jesper Öqvist - 2017-11-24

Fix unbounded ParseName rewrite recursion in lambdas

By creating a bound method access during lambda type inference, a ParseName
rewrite can cause unbounded recursion.  This was caused by using treeCopy()
instead of treeCopyNoTransform().

fixes #203


→ <<cset ba645882c2ab>>
