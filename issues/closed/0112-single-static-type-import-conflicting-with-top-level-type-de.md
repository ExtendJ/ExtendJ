# Single static type import conflicting with top-level type declaration

**Status:** resolved

**JastAddJ 7.1.1-315-g3a38bb0 Java SE 7**

Single static import importing a type with the same name as a top-level type in the local compilation unit should raise an error. This check exists for regular single-type imports already.

## Comments

### Jesper Öqvist - 2015-04-28

Improved import checking

Checking import declarations against locally declared classes now works for
static import declarations as well.

Added attribute ImportDecl.importedTypes(), returning the single type imported
by a single import decl (returns empty set for on-demand imports).

fixes #112 (bitbucket)


→ <<cset 7209d06d71c7>>
