# Parser fails on package annotation with preceding documentation comment

**Status:** resolved

_ExtendJ 8.1.2-55-gfecde64d Java SE 8_

The parser fails on the following `package-info.java` file:

```
/**
 * test
 */
@PackageAnnot package pkg;
```

‌

Expected result: the code should not cause a parsing error.

Actual result: `package-info.java:4,23: error: unexpected token "pkg"`

## Comments

### Jesper Öqvist - 2022-06-28

Handle parsing of package with annotation and docu-comment

fixes #316 (bitbucket)


→ <<cset 77c748df9b14>>
