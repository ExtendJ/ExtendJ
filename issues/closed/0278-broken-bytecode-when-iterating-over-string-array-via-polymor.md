# Broken bytecode when iterating over String array via polymorphic data type

**Status:** resolved

*ExtendJ 8.1.0-27-gcd7effa Java SE 5*

The following program is not executable when compiled with ExtendJ:

```java
// Test using an enhanced-for loop to iterate elements of an
// array stored in a polymorphic container type.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    System.out.println(join(
        new Container<String[]>(
            new String[] { "x", " marks", " the spot" })));
  }

  static String join(Container<String[]> values) {
    StringBuilder message = new StringBuilder();
    for (String part : values.get()) {
      message.append(part);
    }
    return message.toString();
  }

}

class Container<T> {
  private final T value;

  public Container(T value) {
    this.value = value;
  }

  T get() {
    return value;
  }
}
```

Expected result: the program should be runnable and give the output `x marks the spot`.

Actual result: the program fails to run with this error:

```
    [junit] [FAIL] runTest[run/generics/container_06](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Exception in thread "main" java.lang.IncompatibleClassChangeError: Class [Ljava.lang.String; does not implement the requested interface java.lang.Iterable
```

## Comments

### Jesper Öqvist - 2018-01-07

Fix enhanced-for code generation issue

Array types could incorrectly be treated as non-array types due to type erasure
handling in enhanced-for statement code generation.

fixes #278 (bitbucket)


→ <<cset 8af0e3f022bb>>
