# No error reported when using unknown type in variable declaration

**Status:** resolved

*ExtendJ 8.0.1-161-g2081568 Java SE 8*

ExtendJ does not report an error when an unknown type is used in a local variable declaration, as in the following test case:

```java
// An unknown type may not be used as a local variable type.
// .result=COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    org.extendj.Thing thing;  // Unknown type.
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
