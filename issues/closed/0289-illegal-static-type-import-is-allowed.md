# Illegal static type import is allowed

**Status:** resolved

*ExtendJ 8.1.0-48-ge065ff2 Java SE 8*

Test.java:

```java
// It should not be allowed to static-import a package-private type from another package.
// .result: COMPILE_FAIL
import static my.thing.A.Thing; // Error: illegal import.

public class Test { }
```

```java
package my.thing;

public class A {
  static class Thing { }
}
```


Expected result: should fail to compile. Javac gives this error:

```
tests/pkg/static_import_06f/Test.java:3: error: cannot find symbol
import static my.thing.A.Thing; // Error: illegal import.
^
  symbol:   static Thing
  location: class
1 error
```

Actual result: compiles without error.

## Comments

### Jesper Öqvist - 2018-01-11

Improve single-static-import access checking

Add check for importing non-accessible type by single-static-import.

Also added a check that the imported type is static.

fixes #289 (bitbucket)


→ <<cset b15842627181>>
