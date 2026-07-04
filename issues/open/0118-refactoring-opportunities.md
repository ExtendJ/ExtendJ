# Refactoring Opportunities

This is a list of questionable design choices in ExtendJ that are potential candidates for refactoring or redesign. Many of these issues are based on style and opinion, although inconsistent style is to some extent an important concern.

Redesigning or refactoring in ExtendJ is usually not a good idea, unless there is a strong need for it or the change has very little impact on extensions. Almost any change in ExtendJ can affect existing extensions.

##Abstract Grammar Issues

These are flaws related to the Java grammar in ExtendJ:

* The left and right children of `Binary` are named LeftOperand and RightOperand. This is needlessly verbose and inconsistent: `AbstractDot` has children named Left and Right, `AssignExpr` has Source and Dest. In the `AbstractDot` case you wouldn't call the left and right parts operands, but it would still be nice if `Binary` used the same child naming scheme since they are so similar.
* The names of `Binary` and `Unary` is inconsistent with other types of expressions where usually the "Expr" suffix is used.
* ~~`AbstractDot` is not needed in the grammar, and does not seem to have a good purpose.~~
* Some nodes have children with names that end with "Stmt", for example `ForStmt` has InitStmt and UpdateStmt children. This is inconsistent with other types of children where the type of the child is not part of the name (Condition, Left, Right, etc.) In some cases it makes sense to call a child just Stmt, or Expr, but when there is a nice short name for a child already it is just redundant to add a "Stmt" or "Expr" suffix.
* Inconsistent abbreviation of child names. `MethodAccess` has a child `Arg*`, instead of `Argument*`. MethodDecl has a child `Parameter*` not `Param*`.
* `VaraibleDecl` and `VariableDeclaration` are different but similar, and have confusingly similar names.
* `CatchParameterDeclaration` should maybe inherit from `ParameterDeclaration`. If there is a good reason why not then it should be documented.
* The structure of `ArrayTypeAccess` and `ArrayTypeWithSizeAccess` in an array creation expression is counter-intuitive. The outer array type (with size) is the inner-most access in the AST.
* `ParTypeAccess` does not inherit from `TypeAccess`, which is counter-intuitive.

Suggested fixes

* `VariableDeclaration`: TypeAccess:Access -> Type:Access
* Establish style guide for naming AST children, then follow that guide in ExtendJ.

##Attribute Issues

* `TypeDecl.enclosingBlock()` has a confusing name, it should probably be "enclosingBodyDecl" as that represents better what the attribute computes.
* Some attributes are nullable. It would be preferable to introduce null objects or use an option type to represent undefined attribute values.

##Architecture


* Name clash: the generated Beaver parser is called JavaParser, and the interface used in the Frontend class is named JavaParser.
* The type inference system is based on the Java 5 specification for generic method type inference. In Java 7 and Java 8 new forms of type inference were introduced, and the Java specification has been updated to give a more consistent definition of how inference works. The type inference in ExtendJ should be similarly looked over and could probably benefit from a redesign.
* Bytecode generation is mostly based on Java 5 bytecode. The code generator does not yet support stack map frames generation.
* Beaver parser generator
    * Storing modified source files from Beaver in our repository to fix `beaver.Symbol` final field
    * Beaver stores line/column information with too few bits: lossy for larger files
    * Poor support for modularity

##Java Style

* ~~The AST package is named with upper-case letters, this goes against most Java style conventions. ~~ [fixed](https://bitbucket.org/extendj/extendj/commits/50466bc6e4db)

## Comments

### Jesper Öqvist - 2015-07-16

Moved packages to org.jastadd.extendj

Moved AST package to org.jastadd.extendj.ast.
Moved scanner package to org.jastadd.extendj.scanner.
Moved parser package to org.jastadd.extendj.parser.

see #118 (bitbucket)


→ <<cset 50466bc6e4db>>

### Jesper Öqvist - 2015-07-25

Code cleanup

Making sure the JRAG code has consistent layout style.

see #118 (bitbucket)


→ <<cset cd0b63024002>>

### Jesper Öqvist - 2015-07-31

Added JastAdd style guide section to README

see #118 (bitbucket)


→ <<cset 1853c301c00b>>

### Jesper Öqvist - 2017-09-13

Remove AbstractDot from the abstract grammar

The node type AbstractDot has been removed and replaced by the Dot type.  Both
types were previously used for representing qualified expressions.  However, it
was redundant and possibly confusing to have two types for the same purpose.
This change simplifies the abstract grammar by merging both types into one.

This change affects extensions that reference the AbstractDot type.
Extensions using AbstractDot need to be updated to use Dot instead.

This commit is part of an ongoing simplification effort for the ExtendJ
abstract grammar.

see #118 (bitbucket)


→ <<cset 6a0d36dc12e3>>

### Jesper Öqvist - 2018-03-08

Some attributes just delegate to another attribute. For example:

* `TypeDecl.instanceOf(TypeDecl)` -> `TypeDecl.subtype(TypeDecl)`
* `TypeDecl.memberFields(String)` -> `TypeDecl.localFields(String)`

These attributes could be merged.
