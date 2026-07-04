# Control flow / method return value analysis error in nested loop with break

**Status:** resolved

The following test case should not cause a compile error:

```java
import java.util.*;

public class Test {
  // It is okay for non-terminating method to not return a value.
  public String loop1() {
    for (;;) {
    }
  }

  // It is okay for non-terminating method to not return a value.
  public String loop2(List<String> strings) {
    for (;;) {
      for (String p : strings) {
        if (p.equals("foo")) {
          break;
        }
      }
    }
  }
}
```

However, ExtendJ 8.0.1-151-g5196a27 Java SE 8 gives the following error message:

```
Test.java:11: error: the body of a non void method may not complete normally
```

Javac 1.8.0_112-ea does not report an error for this test case.

## Comments

### Jesper Öqvist - 2017-04-06

Improve enhanced-for branch target analysis

Branch target analysis was incorrect for the enhanced for statement, leading to
incorrect statement reachability analysis.

fixes #184 (bitbucket)


→ <<cset 6e54320d4de0>>
