# Passing anonymous class instance to vararg method raises ClassCastException

**Status:** resolved

Short test program that exposes the bug:

````java
public class Test {
	void m() {
		f(new Object() { });
	}
	void f(Object... params) {
	}
}
````

JastAddJ `7.1.1-42-gfcf05c4 Java SE 7` backtrace:

````
java.lang.AssertionError: Compilation failed when expected to pass:
Errors:
Fatal exception while processing tests/type/anonymousdecl/Test.java:
java.lang.ClassCastException: AST.Opt cannot be cast to AST.List
	at AST.AnonymousDecl.getImplementsListNoTransform(AnonymousDecl.java:521)
	at AST.ClassDecl.Define_TypeDecl_hostType(ClassDecl.java:2088)
	at AST.ASTNode.Define_TypeDecl_hostType(ASTNode.java:1723)
	at AST.BodyDecl.hostType(BodyDecl.java:518)
	at AST.ConstructorDecl.accessibleFrom_compute(ConstructorDecl.java:1099)
	at AST.ConstructorDecl.accessibleFrom(ConstructorDecl.java:1091)
	at AST.Expr.chooseConstructor(Expr.java:199)
	at AST.ClassInstanceExpr.decls_compute(ClassInstanceExpr.java:939)
	at AST.ClassInstanceExpr.decls(ClassInstanceExpr.java:930)
	at AST.ClassInstanceExpr.decl_compute(ClassInstanceExpr.java:970)
	at AST.ClassInstanceExpr.decl(ClassInstanceExpr.java:962)
	at AST.ClassInstanceExpr.transformation(ClassInstanceExpr.java:761)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.MethodAccess.refined_Transformations_MethodAccess_transformation(MethodAccess.java:316)
	at AST.MethodAccess.transformation(MethodAccess.java:832)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.MethodDecl.transformation(MethodDecl.java:554)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.ASTNode.transformation(ASTNode.java:570)
	at AST.TypeDecl.transformation(TypeDecl.java:1392)
	at AST.CompilationUnit.transformation(CompilationUnit.java:249)
	at org.jastadd.jastaddj.JavaCompiler.processNoErrors(JavaCompiler.java:39)
	at AST.Frontend.process(Frontend.java:87)
	at org.jastadd.jastaddj.JavaCompiler.compile(JavaCompiler.java:23)
	at org.jastadd.jastaddj.JavaCompiler.main(JavaCompiler.java:18)
````

## Comments

### Jesper Öqvist - 2013-05-31

This is likely caused by an error in JastAdd2.

### Jesper Öqvist - 2013-05-31

I put some code in `getImplementsListNoTransform` to see what children the `AnonymousDecl` had. It turns out that the child at index 3 changes from Opt to List at some point.

### Jesper Öqvist - 2013-05-31

The error is caused by a problem in the JastAdd-generated `fullCopy()` method.

[Link to JastAdd2 issue](https://bitbucket.org/jastadd/jastadd2/issue/131/nta-children-initialized-at-incorrect)

### Jesper Öqvist - 2013-06-02

Updated JastAdd2 for bugfix

fixes #9


→ <<cset 3e94ed6de195>>
