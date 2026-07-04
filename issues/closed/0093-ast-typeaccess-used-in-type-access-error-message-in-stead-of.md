# AST.TypeAccess used in type access error message in stead of accessed type name

**Status:** resolved

**JastAddJ 7.1.1-269-g0f43529 Java SE 7**

This occurred while writing a test for package private type access. The error message in question:

```
tests/pkg/pkgpriv_01f/Test.java:4,2:
  Semantic Error: AST.TypeAccess in Test can not access type p1.A
```

## Comments

### Jesper Öqvist - 2015-02-11

The error is in `TypeAccess.accessControl` declared at line 128 in java4/frontend/AccessControl.jrag. The error message is generated with `this` concatenated.

### Jesper Öqvist - 2015-02-11

Improved type access error message

Pretty print the type access.

fixes #93 (bitbucket)


→ <<cset 22d48cfec732>>
