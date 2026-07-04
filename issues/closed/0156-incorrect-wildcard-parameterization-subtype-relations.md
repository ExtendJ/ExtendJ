# Incorrect wildcard parameterization subtype relations

**Status:** resolved

**ExtendJ 8.0.1-95-gd723ef3**

ExtendJ computes incorrect subtype relations for some wildcard-parameterized types. The following code should compile, but gives an error message:

```java
// Test subtyping relation for wildcard parameterized types.
// .result=COMPILE_PASS
public class Test {
  Klass<?> test(Klass<?> klass) {
    return klass.superklass();
  }

  interface Klass<T> {
    Klass<? super T> superklass();
  }
}
```

The above test case gives the following compile error:

```
    [junit] tests/generics/subtype_01p/Test.java:5: error: return value must be an instance of Test.Klass<wildcards.? extends java.lang.Object> which Test.Klass<wildcards.? super wildcards.? extends java.lang.Object> is not
```

## Comments

### Jesper Öqvist - 2016-03-11

Improve wildcard parameterized subtype checking

fixes #156 (bitbucket)


→ <<cset 48b3902244b6>>
