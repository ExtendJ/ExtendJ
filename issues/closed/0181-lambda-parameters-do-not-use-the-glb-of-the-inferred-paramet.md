# Lambda parameters do not use the GLB of the inferred parameter type

**Status:** resolved

**ExtendJ 8.0.1-139-gb1294d4 Java SE 8**

A lambda expression with an inferred type should use the greatest lower bound (GLB) of the parameter type for the lambda parameter type. For example:

```java
import java.util.stream.Stream;

public class Test {
  void m(Stream<String> stream) {
    stream.map(s -> s.length());
  }
}
```

The type of the lambda expression above is `Function<? super String, R>` and the type of the parameter `s` should be the GLB, i.e. `String`, however ExtendJ assigns the type `? super String` to `s`, as evidenced by the error message:

```
Test.java:8: error: no method named length() in wildcards.? super java.lang.String matches.
```

## Comments

### Jesper Öqvist - 2017-02-03

The Java specification seems to describe the type of a lambda expression to be the non-wildcard parameterization of the target function type.

See [JLS 8 §9.9](https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.9)

### Jesper Öqvist - 2017-02-03

Here is another test case showing the same error more clearly:

```java
import java.util.function.Function;

public class Test {
  Function<? super String, Integer> fun = s -> s.length();
}
```

### Jesper Öqvist - 2017-02-03

Improve function type inference

This improves wildcard type handling in function type inference.

fixes #181 (bitbucket)


→ <<cset e8ccc5411510>>
