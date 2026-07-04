# Remove enum index side effects

**Status:** resolved

**ExtendJ 8.0.1-45-gedaf31d**

ExtendJ adds one method and field for each switch statement with an enum-typed switch expression. This is done in order to transform the switch from an enum-indexed switch to an integer-indexed switch. However, the transformation is currently done using side effects. As usual this has several down-sides and it would thus be nice to remove these side effects.

I have been working on a refactoring to remove these side effects which works by using NTAs to compute the extra fields/methods. These NTAs are used during code generation, but only if there exist enum switch statements.

## Comments

### Jesper Öqvist - 2016-02-19

Replace enum index transformations

Enum indexed switch statements are transformed by ExtendJ to integer indexed
switch statements during code generation. One extra field and method is
generated per switch statement using an enum expression. These are called enum
index fields/methods and they used to be inserted directly into the AST during
the transformation phase using side effects.

This side-effect driven way of inserting enum index fields/methods has been
replaced by using NTAs instead. Using NTAs enables computing the needed fields
and methods on demand during code generation, fully declaratively without using
side effects.

Some new helper attributes were added to complete this refactoring:

* Collection attribute TypeDecl.enumSwitchStatements(). Collects the switch
  statements using an enum expression.
* NTA SwitchStatement.enumIndexExpr(). This is the expression that is generated
  instead of the enum typed expression of a switch statement.
* Synthesized attribute SwitchStmt.constCases(). Gives a list of the case
  labels in a switch statement.
* Synthesized attribute SwitchStmt.enumIndices(). Gives the mapping from enum
  constant to integer switch index.
* NTA TypeDecl.createEnumMethod(SwitchStmt). Replaces the old side-effecty
  method with the same name.
* Synthesized attribute TypeDecl.enumArrayDecl(SwitchStmt). Returns the field
  declarator for the enum index array for the given switch statement.
* NTA TypeDecl.createEnumArrayField(SwitchStmt). Replaces the old side-effecty
  createEnumArray method.

fixes #137 (bitbucket)


→ <<cset 0f26e2672f6c>>
