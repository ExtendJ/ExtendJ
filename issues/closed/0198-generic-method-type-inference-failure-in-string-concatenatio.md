# Generic method type inference failure in string concatenation

**Status:** resolved

*ExtendJ 8.0.1-165-g7fca88c Java SE 8*

The following test case fails:

```java
// Test type inference for simple generic method access.
// .result=COMPILE_PASS
public class Test {
  <T extends Number> T num() {
    return null;
  }

  String numStr() {
    return "num: " + num();
  }
}
```

Error message from ExtendJ:

```
tests/generics/method_14p/Test.java:9: error: no method named num() in Test matches. However, there is a method num()
```

## Comments

### Jesper Öqvist - 2017-04-21

Improve type inference in string concatenation

fixes #198 (bitbucket)


→ <<cset b66a11e7aa36>>
