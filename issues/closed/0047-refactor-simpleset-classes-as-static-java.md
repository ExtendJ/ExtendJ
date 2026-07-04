# Refactor SimpleSet classes as static Java

**Status:** wontfix

The utility SimpleSet classes could just be static Java sources in `src/frontend`.

## Comments

### Jesper Öqvist - 2014-02-18

There are currently some AST classes implementing the SimpleSet interface:

* CatchParameterDeclaration
* FieldDeclaration
* MethodDecl
* ParameterDeclaration
* TypeDecl
* VariableDeclaration

It would really be better if we don't have AST classes who act as SimpleSets, rather they should be contained in SimpleSets. This would avoid the danger of one AST node being added to two sets and then only contained in one.

### Jesper Öqvist - 2016-02-24

There is very little benefit right now to declaring SimpleSet as a regular Java library class. There is no point spending effort on refactoring this, and it may even break extensions if they use inter-type declarations on SimpleSet.

We might revisit this idea at a later time, but right now we won't change it because it is not broken.
