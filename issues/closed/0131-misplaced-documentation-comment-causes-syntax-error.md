# Misplaced documentation comment causes syntax error

**Status:** resolved

ExtendJ 8.0.1-16-g930e00d Java SE 8

This test case causes the parser to generate a syntax error:

```java
// A misplaced documentation comment does not cause a syntax error.
class Test {
  public static interface Listener {
    void m();

    /**
     * Misplaced documentation comment.
     * @param msg a message
     */
  }
}
```

## Comments

### Jesper Öqvist - 2015-12-14

Fix misplaced documentation comment parsing

fixes #131 (bitbucket)


→ <<cset ad3996fda541>>
