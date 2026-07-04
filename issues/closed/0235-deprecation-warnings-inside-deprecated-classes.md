# Deprecation warnings inside deprecated classes

**Status:** resolved

*ExtendJ 8.0.1-234-g7a7bcbc Java SE 8*

Deprecation warnings are not ignored inside a deprecated class.

```java
// Deprecation warnings are not generated for uses of deprecated objects inside
// deprecated code.
// .result: COMPILE_PASS
@Deprecated
public class Test {
  public A a; // No warning should be generated because this is inside a Deprecated class!

  public static void main(String[] args) {

  }
}

@Deprecated
class A { }
```


Expected result: no warnings should be reported.

Actual result: ExtendJ reports a deprecation warning inside the Test class:

```
    [junit] [FAIL] runTest[annotation/deprecated_01p](tests.extendj.TestJava7)
    [junit] Compilation produced unexpected warning:
    [junit] tests/annotation/deprecated_01p/Test.java:6: warning: A has been deprecated
```

## Comments

### Jesper Öqvist - 2017-12-18

Improve deprecation warnings

Suppress deprecation warnings within body declarations of deprecated types.

fixes #235 (bitbucket)


→ <<cset 1ddd00362c14>>
