# Too conservative anonymous class type hierarchy checking

**Status:** resolved

*ExtendJ 8.0.1-153-g6e54320 Java SE 8*

The type hierarchy check should not be applied fully to anonymous class instances, which use a qualifier expression to provide the super-reference.

The following test case should compile:

```java
// Tests a use of inner class instantiation via anonymous class instance
// in separate class.
// .result=COMPILE_PASS

class Outer {
  class IC {
  }
}

public class Test {
  public static void main(String[] args) {
    try {
      // This does not generate a static error, but
      // causes an illegal access error during runtime.
      new Outer().new IC() { };
    } catch (IllegalAccessError e) {
      return;
    }
    throw new Error("Expected IllegalAccessError");
  }
}
```

Currently the above generates the following error message:

```
    [junit] Compilation failed when expected to pass:
    [junit] tests/classes/super_06p/Test.java:0: error: can not generate default constructor for class Anonymous0 because its superclass Outer.IC is an inner type of Outer and Anonymous0 is not an inner type of Outer or a subtype thereof
```

## Comments

### Jesper Öqvist - 2017-04-06

Fix error in anonymous class type hierarchy check

fixes #187 (bitbucket)


→ <<cset af5cf062992d>>
