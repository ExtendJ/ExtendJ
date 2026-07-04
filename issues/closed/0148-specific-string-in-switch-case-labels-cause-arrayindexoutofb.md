# Specific String-in-Switch case labels cause ArrayIndexOutOfBoundsException

**Status:** resolved

**ExtendJ 8.0.1-77-g270d5ec**

A particular set of case labels in a String-in-Switch statement causes an ArrayIndexOutOfBoundsException during code generation. This is a test case triggering the bug:

```java
class Test {
  void f(String s) {
    switch (s) {
      case "#metadataBlock":
        break;
      case "#datasetField":
        break;
      case "#controlledVocabulary":
        break;
    }
  }
}
```

## Comments

### Jesper Öqvist - 2016-03-03

This is caused by the internal `tableSwitchSize` variable overflowing. Fix is to change it to a `long`.

### Jesper Öqvist - 2016-03-03

Fix integer overflow in switch code generation

fixes #148 (bitbucket)


→ <<cset 161c66e2a3fe>>
