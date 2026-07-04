# Broken bytecode for wildcard parameterized method references

**Status:** resolved

*ExtendJ 8.0.1-209-gba64588 Java SE 8*

ExtendJ generates broken bytecode when using a method reference that has a wildcard parameterization.

Test:

```java
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    accept("x", String::toString);
  }

  static <T> void accept(T v, F<? super T> f) {
    f.accept(v);
  }
}

interface F<T> {
  String accept(T v);
}
```

Expected result: should execute without errors

Actual result: Fails to execute with the following output:

```
Exception in thread "main" java.lang.AbstractMethodError: Test$1.accept(Ljava/lang/Object;)Ljava/lang/String;
    [junit]     at Test.accept(Test.java:9)
    [junit]     at Test.main(Test.java:5)
```

This issue is very similar to issue #216, except that it affects method references instead of lambdas.

## Comments

### Jesper Öqvist - 2017-11-27

Another test:

```java
import java.util.*;

public class Test {
  public static void main(String[] args) {
    List<Integer> ints = Arrays.asList(7, 8, 9);
    ints.forEach(System.out::println);
  }
}
```

### Jesper Öqvist - 2017-11-27

Fix error in method reference code generation

The anonymous class implementing a method reference could have an illegal
bytecode signature when the method reference had a wildcard parameterized type.
Wildcards are not allowed in a standalone type declaration, so the non-wildcard
parameterization of the type should be used instead.

Fixed tests:

* jsr335/methodref/type_inf_03p
* jsr335/stream/methodref_01p

fixes #219


→ <<cset d8b3c06addf4>>
