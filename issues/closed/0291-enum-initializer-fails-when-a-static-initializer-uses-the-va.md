# Enum initializer fails when a static initializer uses the values() method

**Status:** resolved

*ExtendJ 8.1.0-52-gddbdc64 Java SE 8*

ExtendJ generates nonexecutable code for this test:

```java
// Test that static initializers for fields in an enum type are run
// after the enum $VALUES field has been initialized.
public class Test {
  public static void main(String[] args) {
    for (EnumInitTest v : EnumInitTest.values) {
      System.out.println(v);
    }
  }
}

enum EnumInitTest {
  HI,
  MOM;

  static EnumInitTest[] values = values();
}
```

Expected result: should print "HI MOM"

Actual result: fails to run with this error:

```
    [junit] [FAIL] runTest[enum/static_03p](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Exception in thread "main" java.lang.ExceptionInInitializerError
    [junit]     at Test.main(Test.java)
    [junit] Caused by: java.lang.NullPointerException
    [junit]     at EnumInitTest.values(Test.java)
    [junit]     at EnumInitTest.<clinit>(Test.java)
    [junit]     ... 1 more]>
```

## Comments

### Jesper Öqvist - 2018-01-11

Reorder enum initializer code generation

This fixes an enum initialization error where static initializers that depended
on the implicit $VALUES field were generated before the $VALUES field
initializer.

To fix the error we now always generate the $VALUES initializer first, before
other static initializers.

fixes #291 (bitbucket)


→ <<cset 027b9c763847>>
