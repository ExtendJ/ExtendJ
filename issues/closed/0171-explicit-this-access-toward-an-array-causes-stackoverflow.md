# explicit this access toward an array causes stackoverflow

**Status:** resolved

This code causes a stackoverflow :

```
#!java
package p;
public class C {
    int[] array;
    final void m() { int l = this.array.length; }
}
```

## Comments

### Jesper Öqvist - 2016-04-26

I could not reproduce the stack overflow error in the two latest commits when compiling the code with the Java 8 version of ExtendJ. What version gives you stack overflow?

### Loïc Girault - 2016-04-28

I'm using commit 05749c5.
Here is the trace of the stack overflow error with one iteration of the cycle begining with org.extendj.ast.ParseName.rewriteTo(ParseName.java:326)
```
java.lang.StackOverflowError
        at org.extendj.ast.VarDeclStmt.Define_nameType(VarDeclStmt.java:647)
        at org.extendj.ast.ASTNode.Define_nameType(ASTNode.java:3425)
        at org.extendj.ast.Declarator.Define_nameType(Declarator.java:1109)
        at org.extendj.ast.ASTNode.Define_nameType(ASTNode.java:3425)
        at org.extendj.ast.AbstractDot.Define_nameType(AbstractDot.java:1116)
        at org.extendj.ast.Expr.nameType(Expr.java:1923)
        at org.extendj.ast.ParseName.rewriteRule0(ParseName.java:335)
        at org.extendj.ast.ParseName.rewriteTo(ParseName.java:326)
        at org.extendj.ast.ParseName.rewrittenNode(ParseName.java:419)
        at org.extendj.ast.ASTNode.getChild(ASTNode.java:1503)
        at org.extendj.ast.Dot.getRight(Dot.java:276)
        at org.extendj.ast.Dot.Define_isRightChildOfDot(Dot.java:335)
        at org.extendj.ast.Expr.isRightChildOfDot(Expr.java:1890)
        at org.extendj.ast.Access.hasPrevExpr(Access.java:344)
        at org.extendj.ast.Access.isQualified(Access.java:311)
        at org.extendj.ast.ThisAccess.decl_compute(ThisAccess.java:318)
        at org.extendj.ast.ThisAccess.decl(ThisAccess.java:310)
        at org.extendj.ast.ThisAccess.type(ThisAccess.java:381)
        at org.extendj.ast.Expr.qualifiedLookupVariable(Expr.java:841)
        at org.extendj.ast.AbstractDot.Define_lookupVariable(AbstractDot.java:1097)
        at org.extendj.ast.Expr.lookupVariable(Expr.java:1868)
        at org.extendj.ast.ParseName.resolveAmbiguousName(ParseName.java:123)
        at org.extendj.ast.ParseName.rewriteRule0(ParseName.java:362)
        at org.extendj.ast.ParseName.rewriteTo(ParseName.java:326)
```

### Jesper Öqvist - 2016-05-15

@lorilan What version of JastAdd are you using? I think you need to update to JastAdd 2.2.2 to fix this error, and use the `--rewrite=cnta` and `--safeLazy` options.

### Loïc Girault - 2016-05-17

I'm using JastAdd 2.2.2 but indeed the ```--safeLazy``` was missing. Using it fix the problem ! thank you very much.

But I find it odd that it's not the default behavior. Is it more costly to use this option ?

### Jesper Öqvist - 2016-05-17

@lorilan I would prefer if `--safeLazy` was on by default, but it does add a slight performance cost so for the time being it is not a default option. There was also a concern that the way it changes attribute evaluation could affect old JastAdd projects that relied on attribute evaluation order (which they should not, but it happens anyway).
