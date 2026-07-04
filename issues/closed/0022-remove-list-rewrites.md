# Remove List Rewrites

**Status:** resolved

Stop using list rewrites in JastAddJ!

List rewrites are used to rewrite variable declarations, field declarations and add implicit constructors. The list rewrites could be replaced by nonterminal attributes.

## Comments

### Jesper Öqvist - 2013-10-15

List rewrites to add implicit constructors have been removed, now list rewrites that rewrite field and variable declarations should be replaced by NTAs.

Rewrites for variable and field declarations seem to reside in the file `VariableDeclaration.jrag`

### Jesper Öqvist - 2013-10-22

Remove implicit constructor list rewrites

see issue #22 (bitbucket)


→ <<cset 2fc7e61e4d86>>

### Jesper Öqvist - 2013-10-23

Remove variable declaration rewrites

see issue #22 (bitbucket)


→ <<cset 26234ce6a5e7>>

### Jesper Öqvist - 2014-06-05

Remove implicit constructor list rewrites

see issue #22 (bitbucket)


→ <<cset aedcfa493b95>>

### Jesper Öqvist - 2014-06-05

Remove variable declaration rewrites

- VarDeclStmt is no longer rewritten to a separate VariableDeclaration statements.
- VarDeclStmt now has an NTA list of single declarations named SingleDecl
- VariableDeclaration no longer inherits from Stmt.

Since VariableDeclaration is now a child of VarDeclStmt some inherited
attributes needed to be changed in order to properly propagate values to
VariableDeclaration nodes.

see issue #22 (bitbucket)


→ <<cset f24288ffa10d>>

### Jesper Öqvist - 2016-02-16

The `FieldDecl` list rewrite was removed in commit 9cfd7f7c6d0d165a0de34d24a13f867dfbe818e3

This was the last remaining list rewrite in ExtendJ.

The AST was refactored a bit in order to remove the list rewrite. First, `VariableDeclaration` was removed from the abstract grammar in 3107b2352a298ccb9c9d8274c843c7c01e0def66, then the `VariableDecl` node was replaced by the `VariableDeclarator` node and `FieldDeclaration` was replaced by `FieldDeclarator`. Both `VariableDeclarator` and `FieldDeclarator` inherit from the same abstract node type `Declarator`, which does not inherit from statement or body decl like `VariableDeclaration` and `FieldDeclaration` did. This means that `EnumConstant` no longer could inherit some attribute declarations from `FieldDeclaration`. A more detailed description of these changes will be compiled in the extension migration guide later.
