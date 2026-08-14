# Diamond - no method matches

**Status:** resolved

This example causes an error:

```java
import java.util.*;

public class Diamond {
  public static void main(String[] args) {
    Set<String> hashSet01 = new HashSet<>();
    Set<String> hashSet02 = new HashSet<>();
    hashSet02.addAll(new ArrayList<>(hashSet01)); // new ArrayList<String>(hashSet01) works
  }
}
```

Actual result:

```
Diamond.java:6: error: no method named addAll(java.util.ArrayList<java.lang.Object>)
  in java.util.Set<java.lang.String> matches. However, there is a method
  addAll(java.util.Collection<wildcards.? extends java.lang.String>)
```

## Comments

### Jesper Öqvist - 2017-04-22

This test passes with ExtendJ 8.0.1-168-gef13696 Java SE 8.

Thank you for the test case, I have added it to the regression test suite.

### Jesper Öqvist - 2017-04-25

The test fails with the Java 7 build of ExtendJ (passes in the Java 8 build only).

### Jesper Öqvist - 2026-08-14

Fixed by d4d25af7 ("Improve type inference for diamond expressions", 2019-03-14),
which made type variable substitution substitute inside type bounds.

The same commit fixed issue 307, whose tests in `rtest/tests/jsr334/diamond`
cover the type bound substitution it introduced.

The regression test `rtest/tests/jsr334/diamond/diamond_24p` is the one referred to in
the 2017-04-22 comment and it presently runs fine on the Java 7 build of ExtendJ.
