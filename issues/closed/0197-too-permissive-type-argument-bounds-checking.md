# Too permissive type argument bounds checking

**Status:** resolved

*ExtendJ 8.0.1-161-g2081568 Java SE 8*

The type argument bounds check is too permissive, allowing argument types that are not within the generic type bounds. For example, the following test case should not compile:

```java
// Test for generic bounds checking.
// .result=COMPILE_FAIL
public class Test {
  Fun<String, ?> fun;  // Error: String is not within the bounds of I.
}

interface Fun<I extends R, R> {
  R fun(I i);
}
```

## Comments

### Jesper Öqvist - 2017-04-20

Substitute type variables in type bound checking

This adds substitution for type variables in the type argument bounds checking,
resulting in improved accuracy in type bound checking.

The new AST class SubstitutedTypeVariable has been added to represent type
variables that should have their bound types substituted.
A new NTA list child named SubstTypeParam has been added to ParTypeDecl and
ParInterfaceDecl, containing substituted type variables.

Also fixed incorrect subtype equations for wildcard and wildcard extends types.

fixes #197 (bitbucket)


→ <<cset e0c932d47d7f>>
