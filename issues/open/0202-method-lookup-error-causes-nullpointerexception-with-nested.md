# Method lookup error causes NullPointerException with nested lambdas

*ExtendJ 8.0.1-174-g53debda Java SE 8*

The following test case should generate an error message instead of a NullPointerException:

```java
import java.util.function.Function;

interface StringTransformer {
  String transform(String str, Function<String, String> fun);
  String replaceWords(String str, Function<String, String> fun);
}

public class Lambda2 {
  public String replace(String input, StringTransformer transformer) {
    return transform(input, str -> replaceWords(str, word -> "foo"));
  }
}
```

The `transform` method access is incorrect - it should be qualified by `transformer`. However, this test case should result in a nice error message instead of the following:

```
Fatal exception:
java.lang.NullPointerException
        at org.extendj.ast.LambdaExpr.toClass(LambdaExpr.java:334)
        at org.extendj.ast.LambdaExpr.anonymousDecl(LambdaExpr.java:350)
        at org.extendj.ast.LambdaExpr.Define_enclosingLambdaType(LambdaExpr.java:1507)
        at org.extendj.ast.ASTNode.Define_enclosingLambdaType(ASTNode.java:4113)
        at org.extendj.ast.InferredParameterDeclaration.enclosingLambdaType(InferredParameterDeclaration.java:800)
        at org.extendj.ast.InferredParameterDeclaration.hostType(InferredParameterDeclaration.java:608)
        at org.extendj.ast.VarAccess.nameProblems(VarAccess.java:807)
        at org.extendj.ast.VarAccess.contributeTo_CompilationUnit_problems(VarAccess.java:1280)
        at org.extendj.ast.CompilationUnit.problems_compute(CompilationUnit.java:1662)
        at org.extendj.ast.CompilationUnit.problems(CompilationUnit.java:1641)
        at org.extendj.ast.CompilationUnit.errors(CompilationUnit.java:696)
        at org.extendj.ast.Frontend.processCompilationUnit(Frontend.java:217)
        at org.extendj.JavaCompiler.processCompilationUnit(JavaCompiler.java:107)
        at org.extendj.ast.Frontend.run(Frontend.java:142)
        at org.extendj.JavaCompiler.run(JavaCompiler.java:101)
        at org.extendj.JavaCompiler.main(JavaCompiler.java:61)
        at org.jastadd.extendj.JavaCompiler.main(JavaCompiler.java:39)
```

## Comments

### Jesper Öqvist - 2017-11-24

The original test case is: `jsr335/lambda/err_01f`.

Added another test (`jsr335/lambda/err_02f`), which does not use type inference:

```java
import java.util.function.Function;

public interface Test {
  String transform(String in, Function<String, String> fun);

  default void prepend(String string) {
    transform(missing, t -> t);
  }
}
```

### Jesper Öqvist - 2017-11-24

The problem is caused by `LambdaExpr.targetInterface()` being nullable, but used without a null guard in creating an anonymous class for lambda expressions. See java8/frontend/LambdaAnonymousDecl.jrag:

```java
  syn nta ClassInstanceExpr LambdaExpr.toClass() =
      new ClassInstanceExpr(
          targetInterface().createQualifiedAccess(),
          new List<Expr>(),
          new Opt<TypeDecl>(buildAnonymousDecl()));
```
