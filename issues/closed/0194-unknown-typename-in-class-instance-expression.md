# Unknown typename in class instance expression

**Status:** resolved

*ExtendJ 8.0.1-161-g2081568 Java SE 8*

A class instance expression must refer to a valid typename, or an error should be reported.

According to [JLS 8 §15.9.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.9.1):

> The ClassOrInterfaceTypeToInstantiate must denote a class that is accessible, non-final, and not an enum type; or denote an interface that is accessible. Otherwise a compile-time error occurs.


ExtendJ currently does not give an error when the typename does not denote a type:

```java
// Unknown typename in class instance expression.
// .result=COMPILE_FAIL

public class Test {
  static void f() {
    Test bort = new Test();
    new bort.Test();  // Error bort.Test is not a typename.
  }
}
```

## Comments

### Jesper Öqvist - 2017-04-24

Report errors for uses of unknown qualified types

Improved error checking for type accesses to handle unknown qualified types.

Adjusted the error messages for the inaccessible type error and the ambiguous
type error.

fixes #192 (bitbucket)
fixes #193 (bitbucket)
fixes #194 (bitbucket)
fixes #195 (bitbucket)
fixes #196 (bitbucket)


→ <<cset 53debda90fb3>>
