# Type checking error for enhanced-for loop iterating over wildcard extends type with primitive loop variable type

**Status:** resolved

*ExtendJ 8.1.0-45-g5c1c58b Java SE 5*

```java
// Test that enhanced-for can iterate over a an iterable type parameterized
// with a wildcard extends type.
// Autoboxing is used to convert the element type to the loop variable type.
// .result: COMPILE_PASS
public class Test {
  public void printInts(Iterable<? extends Integer> ints) {
    for (int i : ints) {
      System.out.println(i);
    }
  }
}
```

Expected result: should compile.

Actual result: compilation errors:

```
    [junit] Compilation failed when expected to pass:
    [junit] tests/generics/enhanced_for_02p/Test.java:7: error: parameter of type int can not be assigned an element of type ? extends Integer
```

## Comments

### Jesper Öqvist - 2018-01-10

Improve wildcard extends assignment conversion

fixes #284 (bitbucket)


→ <<cset de5c65bcc79c>>

### Jesper Öqvist - 2018-01-11

Improve wildcard extends assignment conversion

fixes #284 (bitbucket)


→ <<cset 6dab8683ae9e>>
