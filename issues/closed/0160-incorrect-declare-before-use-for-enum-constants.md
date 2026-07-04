# Incorrect declare-before-use for enum constants

**Status:** resolved

**ExtendJ 8.0.1-101-gf92bc19**

Enum constants are always declared before being used after the enum constant part of an enum declaration.

Test case:

```java
// Enum constants can be used in a static context.
// .result=COMPILE_PASS
enum Test {
  O1, O2, O3;

  static {
    System.err.println(O1);
  }
}
```

ExtendJ error:

```
    [junit] tests/enum/static_02p/Test.java:7,24: error: variable O1 is used in static {
    [junit]   System.err.println(O1);
    [junit] } before it is declared
```

## Comments

### Jesper Öqvist - 2016-03-14

Fix enum constant declare-before-use analysis

fixes #160 (bitbucket)


→ <<cset d0b096f71901>>
