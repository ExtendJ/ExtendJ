# Missing pretty print for VariableDeclaration

**Status:** resolved

VariableDeclaration has no prettyPrint method. Here is an implementation :

```
#!java

public void VariableDeclaration.prettyPrint(PrettyPrinter out) {
    getModifiers().prettyPrint(out);
    getTypeAccess().prettyPrint(out);
    out.print(" ");
    out.print(getID());
    if (hasInit()) {
      out.print(" = ");
      out.print(getInit());
    }
    out.print(";");
}
```

## Comments

### Jesper Öqvist - 2015-12-04

Add prettyPrint method for VariableDeclaration

Also fixed error message for field definite assignment errors.

fixes #130 (bitbucket)


→ <<cset 52b36e957ec1>>

### Loïc Girault - 2015-12-04

Also about VariableDeclaration, the following equation in LookupVariable.jrag seems to be missing :

```
#!java

eq VariableDeclaration.variableDeclaration(String name) = declaresVariable(name) ? this : null ;
```
I haven't looked into the parser, I suppose it generates a VarDeclStmt even when a VariableDeclaration would suffice. This could explain why the above equation is not needed.  Since I'm doing refactoring in the AST I introduced directly a VariableDeclaration and had a bug because of this missing (?) equation.

### Jesper Öqvist - 2015-12-04

Yes, the parser only builds `VarDeclStmt` nodes with `VariableDecl` children. `VarDeclStmt` has a non-terminal list child containing `VariableDeclaration` nodes (`SingleDecl`), and name lookup always points to the non-terminal `VariableDeclaration` nodes. I wish `VariableDecl` and `VariableDeclaration` had more distinct names, but it might not be worth it to rename them now. A better naming scheme might be to rename `VariableDeclartion` to `VariableDeclNta`.

Edit: oops, the name lookup actually points to `VariableDeclaration` nodes.

### Loïc Girault - 2015-12-04

l don't get the following defintion in java.ast
VarDeclStmt : Stmt ::= Modifiers TypeAccess:Access VariableDecl\* /SingleDecl:VariableDeclaration\*/;
Is SingleDecl meant to be an alias for when the size of the VariableDecl list is one ?

### Jesper Öqvist - 2015-12-04

`/SingleDecl:VariableDeclaration*/` is a list of `VariableDeclaration` that is non-terminal, meaning that the nodes in the list are computed by an attribute. `SingleDecl` is the name of the list.

### Loïc Girault - 2015-12-04

Thank you for your answer. I understand the jastadd syntax, but I mean in this case, for which attribute does SingleDecl exist ? With a quick grep, it seems that everywhere SingleDecl is used, the VariableDecl list should have been used instead.

### Jesper Öqvist - 2015-12-04

The `SingleDecl` list is used as a way of avoiding a JastAdd rewrite. `VarDeclStmt` used to be rewritten to a list of `VariableDeclaration`, but this type of list rewrite is unsafe to use so instead individual `VariableDeclaration` nodes are computed by the `VarDeclStmt.getSingleDecl(int)` attribute. This means that the `VariableDecl` children of `VarDeclStmt` do not need to be used at all, since they have less information (modifiers, type) than `VariableDeclaration`.

### Jesper Öqvist - 2015-12-05

I feel stupid now. This discussion made me realise that a better solution for ExtendJ would be to completely remove `VariableDeclaration` from the abstract grammar, and instead add NTA type and modifier children to `VariableDecl`:

    VariableDecl ::= /Modifiers/ /TypeAccess:Access/ <ID:String> Dims* [Init:Expr];

### Loïc Girault - 2015-12-05

Well wasnt this caused because extendj is supposed to map the java specification ? and Modifiers and TypeAcccess shouldn't be optional rather than non terminal ? And making VariableDecl a Stmt wouldn't break anything elsewhere ?

Anyway, I'm happy if my questions help you find some improvements for extendj
