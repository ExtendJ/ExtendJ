# LCTA not defined

**Status:** resolved

When compiling jython 2.7b1 with JastAddJ `7.1.1-29-g550f24d Java SE 6` and `7.1.1-29-g550f24d Java SE 7` I get the following error message:

````
Fatal exception while processing tmp/src/jython-2.7b1/src/org/python/core/PyJavaType.java:
java.lang.Error: lcta not defined for (AST.WildcardExtendsType, AST.ClassDecl
	at AST.LUBType.lcta(LUBType.java:218)
	at AST.LUBType.lci(LUBType.java:168)
	at AST.LUBType.lub_compute(LUBType.java:720)
	at AST.LUBType.lub(LUBType.java:706)
	at AST.Constraints.resolveSupertypeConstraints(Constraints.java:192)
	at AST.MethodAccess.computeConstraints(MethodAccess.java:361)
	at AST.MethodAccess.typeArguments_compute(MethodAccess.java:1327)
	at AST.MethodAccess.typeArguments(MethodAccess.java:1316)
	at AST.MethodAccess.potentiallyApplicable(MethodAccess.java:1275)
	at AST.MethodAccess.potentiallyApplicable(MethodAccess.java:399)
	at AST.MethodAccess.maxSpecific(MethodAccess.java:703)
	at AST.MethodAccess.decls_compute(MethodAccess.java:1012)
	at AST.MethodAccess.decls(MethodAccess.java:1004)
	at AST.MethodAccess.decl_compute(MethodAccess.java:1046)
	at AST.MethodAccess.decl(MethodAccess.java:1038)
	at AST.MethodAccess.refined_TypeAnalysis_MethodAccess_type(MethodAccess.java:831)
	at AST.MethodAccess.type_compute(MethodAccess.java:1182)
	at AST.MethodAccess.type(MethodAccess.java:1167)
	at AST.AbstractDot.type_compute(AbstractDot.java:618)
	at AST.AbstractDot.type(AbstractDot.java:611)
	at AST.FieldDeclaration.typeCheck(FieldDeclaration.java:301)
	at AST.ASTNode.collectErrors(ASTNode.java:297)
	at AST.ASTNode.collectErrors(ASTNode.java:304)
	at AST.ASTNode.collectErrors(ASTNode.java:304)
	at AST.ASTNode.collectErrors(ASTNode.java:304)
	at AST.ASTNode.collectErrors(ASTNode.java:304)
	at AST.CompilationUnit.errorCheck(CompilationUnit.java:183)
	at AST.Frontend.process(Frontend.java:76)
	at org.jastadd.jastaddj.JavaCompiler.compile(JavaCompiler.java:23)
	at org.jastadd.jastaddj.JavaCompiler.main(JavaCompiler.java:18)
````

## Comments

### Jesper Öqvist - 2013-05-30

The JLS states that there are 6 cases for lcta, but three of those are symmetrical and that is not handled correctly in JastAddJ. Should be fixable by creating symmetrical lcta rules.

[Relevant JLS section.](http://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.12.2.7)

### Jesper Öqvist - 2013-05-30

Fixed lcta calculation error

- cover all symmetrical cases for lcta
- fixes #6


→ <<cset fcf05c4b0c9d>>
