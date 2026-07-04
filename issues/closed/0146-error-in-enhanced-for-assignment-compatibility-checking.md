# Error in enhanced for assignment compatibility checking

**Status:** resolved

**ExtendJ 8.0.1-73-g4a602e2**

There is a problem in the assignment compatibility check for enhanced for statements causing the following legal code to fail to compile:

```java
import java.util.EnumSet;

class Test {
  void test(EnumSet<?> set) {
    for (Enum e : set) {
    }
  }
}
```

The error message is:

```
tests/stmt/enhanced_for_01p/Test.java:7: error: parameter of type java.lang.Enum can not be assigned an element of type E
```

This seems to be caused by the type analysis not computing the correct element type for the iterable `set` expression.

## Comments

### Jesper Öqvist - 2016-03-01

Modified wildcard type argument substitution

Type argument substitution when using a wildcard type argument should not
substitute using a plain wildcard type, instead a wildcard extends type should
be used.

fixes #146 (bitbucket)


→ <<cset 89f14498c6e0>>
