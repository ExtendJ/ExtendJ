# Replace nestedTypes by NTA

**Status:** resolved

The `TypeDecl.nestedTypes` collection is used to track accessor classes that need to be generated. This collection is filled in during bytecode generation, but it could just as well be a nonterminal list.

## Comments

### Jesper Öqvist - 2013-10-01

The nestedTypes collection also contains regular nested types (not only accessor classes).

### Jesper Öqvist - 2016-02-24

Create accessors using NTAs

Replaced the side-effect driven creation of various accessor methods by
side-effect free NTAs.

* Removed TypeDecl.usedNestedTypes().
* Changed TypeDecl.nestedTypes() into a collection attribute.
* Removed ASTNode.transformation() and all of its overriding methods.
* Added the attributes MethodAccess.transformed(), AbstractDot.transformed(),
  ConstructorAccess.transformed(), and ClassInstanceExpr.transformed().
  These attributes are used to access the transformed versions of these
  expressions. The transformed versions are generated using NTAs to
  avoid side effects.

fixes #26 (bitbucket)
fixes #27 (bitbucket)


→ <<cset 5cb860a2d446>>
