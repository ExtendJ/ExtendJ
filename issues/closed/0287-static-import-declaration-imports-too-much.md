# Static import declaration imports too much

**Status:** resolved

*ExtendJ 8.1.0-47-gdeb1b1c Java SE 8*

The following test should fail to compile:

Test.java:

```java
// .result: COMPILE_PASS
import static pkg.A.newB;

public class Test {
  B fail = newB(); // Error: B not imported!
}
```

A.java:

```java
package pkg;

public class A {
  public static class B extends A {
  }

  public static B newB() {
    return new B();
  }
}
```

Expected result: should fail to compile:

```
    [junit] Compilation failed when expected to pass:
    [junit] tests/pkg/static_import_05f/Test.java:5: error: cannot find symbol
    [junit]   B fail = newB(); // Error: B not imported!
    [junit]   ^
    [junit]   symbol:   class B
    [junit]   location: class Test
    [junit] 1 error
```

Actual result: ExtendJ compiles the code without errors.

## Comments

### Jesper Öqvist - 2018-01-11

Fix error in single-static-import type lookup

fixes #287 (bitbucket)


→ <<cset e065ff2e622a>>
