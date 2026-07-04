# Crash when compiling Antlr in the QualitasCorpus

**Status:** resolved

ExtendJ 8.0.1-220-gfec29ac Java SE 8

When compiling the Antlr project from the QualitasCorpus ExtendJ crashes with a NullpointerException. The stacktrace is attached in the issue.

I compiled using:
java -Xss1024m -Xms4096m -jar extendj.jar @systems_10\antlr-4.0

## Attachments

- [crash2.txt](../attachments/251/crash2.txt) (uploaded by Sebastian Hjelm)

## Comments

### Jesper Öqvist - 2017-12-28

Here is a small test case that provokes the same crash:

```java
public class Er1 {
  public static void main(String[] args) {
    assertEqulas(10, ident("bar"));
  }

  static <U, V> V ident(U t) {
    return t;
  }
}
```

### Jesper Öqvist - 2017-12-29

Issue #252 was marked as a duplicate of this issue.

### Jesper Öqvist - 2017-12-29

Fix out of bounds parameter indexing issues

When accessing a method or constructor parameter type, the parameter index was
in some cases out of bounds of the actual parameter list. This has been fixed
by introducing a helper attribute to safely compute the type of a parameter.

fixes #251 (bitbucket)


→ <<cset 01844b25de36>>
