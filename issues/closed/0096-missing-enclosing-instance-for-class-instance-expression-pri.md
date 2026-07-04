# Missing enclosing instance for class instance expression prints stack trace

**Status:** resolved

**JastAddJ 7.1.1-271-g22d48cf Java SE 7**

JastAddJ should print a regular error message, and not a stack trace, for the following error:

```
cat test/J9.java
class J9 {
        class D {
        }
}
class C {
        void m () {
                J9.D d = new J9.D();
        }
}
```

The error output currently:

```
C: AST.MethodDecl
Errors:
Fatal exception:
java.lang.Error: test/J9.java:7:
  *** Semantic Error: Could not find enclosing for AST.ClassInstanceExpr
        at AST.ClassInstanceExpr.emitInnerMemberEnclosing(ClassInstanceExpr.java:274)
        at AST.ClassInstanceExpr.createBCode(ClassInstanceExpr.java:311)
        at AST.VariableDeclaration.emitInitializerBCode(VariableDeclaration.java:125)
        at AST.VariableDeclaration.createBCode(VariableDeclaration.java:115)
        at AST.VarDeclStmt.createBCode(VarDeclStmt.java:58)
        at AST.Block.createBCode(Block.java:45)
        at AST.MethodDecl.createBCode(MethodDecl.java:230)
        at AST.MethodDecl.generateBytecodes(MethodDecl.java:217)
        at AST.MethodDecl.bytecodes_compute(MethodDecl.java:1800)
        at AST.MethodDecl.bytecodes(MethodDecl.java:1784)
        at AST.MethodDecl.refined_Attributes_MethodDecl_attributes(MethodDecl.java:1109)
        at AST.MethodDecl.refined_AnnotationsCodegen_MethodDecl_attributes(MethodDecl.java:1145)
        at AST.MethodDecl.attributes_compute(MethodDecl.java:1709)
        at AST.MethodDecl.attributes(MethodDecl.java:1696)
        at AST.MethodDecl.touchMethod(MethodDecl.java:256)
        at AST.ClassDecl.generateClassfile(ClassDecl.java:344)
        at AST.CompilationUnit.generateClassfile(CompilationUnit.java:143)
        at org.jastadd.jastaddj.JavaCompiler.processNoErrors(JavaCompiler.java:119)
        at AST.Frontend.run(Frontend.java:186)
        at org.jastadd.jastaddj.JavaCompiler.run(JavaCompiler.java:85)
        at org.jastadd.jastaddj.JavaCompiler.main(JavaCompiler.java:34)
```

This error also happens during code generation so it would be missed if only semantic checking is done.

## Comments

### Jesper Öqvist - 2015-02-18

Added test `jjtest/tests/jastaddj/enclosing_01f` for this error message issue.

### Jesper Öqvist - 2015-02-18

This was fixed in commit 701eb1351faf9b20ea4c5a18198a23bae239d112

The commit message mentioned the wrong issue number, so this issue was unfortunately left open. It is too late to edit the commit now, so I'm just closing this manually. The mentioned test now acts as regression test for this issue.
