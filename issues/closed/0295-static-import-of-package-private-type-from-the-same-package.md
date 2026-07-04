# Static import of package-private type from the same package

**Status:** resolved

*ExtendJ 8.1.0-58-gbbb70a7 Java SE 8*

ExtendJ incorrectly gives an error for this program:

Test.java:

```java
// It is allowed to static-import a package-private type from the same package.
//
// .result: COMPILE_PASS
package my.thing;

import static my.thing.A.Thing; // OK: same package.

public class Test { }
```

A.java:

```java
package my.thing;

public class A {
  static class Thing { }
}
```

Expected result: no compile error.

Actual result: ExtendJ reports an error:

```
tests/pkg/static_import_09p/Test.java:6: error: can not access non-public type my.thing.A.Thing
```

## Comments

### Jesper Öqvist - 2018-01-19

Fix errors in single-static-import access rules

This allows single-static-imports to import non-public types from the
same package.

fixes #295 (bitbucket)


→ <<cset e8fad423f359>>
