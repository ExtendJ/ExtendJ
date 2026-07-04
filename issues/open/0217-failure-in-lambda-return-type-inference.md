# Failure in Lambda return type inference

*ExtendJ 8.0.1-204-ga9ab455 Java SE 8*

Test:

```java
// .result: COMPILE_PASS
import java.util.function.Function;

public class Test {
  public static void main(String[] args) {
    int res = map(1, e->e);
  }

  static <T, R> R map(T e, Function<T, R> f) {
    return f.apply(e);
  }
}
```

Expected result: no errors

Actual result: ExtendJ fails to infer the correct type for the generic method:

```
    [junit] Compilation failed when expected to pass:
    [junit] tests/jsr335/lambda/type_inf_06p/Test.java:7: error: can not assign variable res of type int a value of type java.lang.Object
```

## Comments

### Jesper Öqvist - 2017-11-22

Initial constraints produced in the current implementation:

* `T :> Integer`
* `T = T`
* `R = R`

These initial constraints are produced from just the `map` arguments. Since there exist equality constraints, the inference does not continue to looking at the return type. This prevents it from inferring that `R` should have type `Integer`.

### Jesper Öqvist - 2017-11-23

This seems to be a case where the new type inference from Java 8 is needed. See [JLS 8 §18.](https://docs.oracle.com/javase/specs/jls/se8/html/jls-18.html)

Both the constraints from the map call and the lambda type inference constraints should be solved at the same time to find the solution in this case.
