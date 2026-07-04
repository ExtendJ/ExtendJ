# Remove side effects in ExtendJ

**Status:** resolved

The ongoing work with removing side effects in JastAddJ's AST consists of many smaller changes that will be documented in separate issues.

Tasks include:

* Remove `transformation()` method which creates accessor methods in `TypeDecl`s.
* Remove list rewrites #22
* Remove `nestedTypes()` #26
* Remove rewrites that depend on side effects (see NameResolution aspect)
* Remove `addEnclosingVariables()` #29
* Remove `createAssertionsDisabled()` #30
* Remove `EnumDecl.addValues()` which adds implicit fields and methods to enum types (`$VALUES`, `values()`, `valueOf(int)`).
* Remove enum constructor rewrite which adds parameters to existing enum constructors.
* Replace the side-effect driven adding of enum switch maps (`$SwitchMap$...`).
* Replace side effects used to transform enum switch statements to use enum switch index maps.
* Remove enclosing variable transformation side effects. Enclosing variables could be implicitly generated during code generation instead.
* Remove `ASTNode.clear()`, which clears AST nodes after code generation.

## Comments

### Jesper Öqvist - 2013-10-10

Added subtasks

### Jesper Öqvist - 2013-10-28

Updated description

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

### Jesper Öqvist - 2016-02-24

Missed one side effect: `ASTNode.clear()`, which clears AST nodes after code generation.

### Jesper Öqvist - 2016-02-24

Refactoring to enable using circular rewrites

Refactored attributes to enable JastAdd circular NTA rewrites.

Removed ASTNode.clear(), which was used to remove nodes from the AST after
code generation.

fixes #27 (bitbucket)


→ <<cset 1f396ed5495c>>
