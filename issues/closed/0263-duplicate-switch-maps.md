# Duplicate switch maps

**Status:** resolved

*ExtendJ 8.1.0-4-ge296850 Java SE 7*

ExtendJ generates broken bytecode for this test:

```java
// Test that multiple switch statements do not lead to duplicated switch map.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    test(Egg.TURKEY);
    test(Egg.TURTLE);
    test(Egg.FISH);
  }

  static void test(Egg egg) {
    System.out.format("%s egg weighs %dg and is %s%n", egg, weight(egg), color(egg));
  }

  static String color(Egg egg) {
    switch (egg) {
      case TURKEY:
        return "White";
      case TURTLE:
        return "green";
      case FISH:
        return "orange";
    }
    return "???";
  }

  static int weight(Egg egg) {
    switch (egg) {
      case TURKEY:
        return 1000;
      case TURTLE:
        return 200;
      case FISH:
        return 2;
    }
    return -1;
  }
}

enum Egg {
  TURKEY,
  TURTLE,
  FISH,
}
```

The issue is caused by duplicate implicit switch map fields generated in the class `Test`.

Expected result: should execute without error

Actual result: runtime error:

```
    [junit] [FAIL] runTest[enum/switch_02p](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Exception in thread "main" java.lang.ClassFormatError: Duplicate field name&signature in class file Test
    ...
```

Here is the relevant part of the bytecode:

```
  static final int[] $SwitchMap$Egg;
    flags: ACC_STATIC, ACC_FINAL, ACC_SYNTHETIC

  static final int[] $SwitchMap$Egg;
    flags: ACC_STATIC, ACC_FINAL, ACC_SYNTHETIC
```

## Comments

### Jesper Öqvist - 2018-01-02

The switch map initializing method is also duplicated.

### Jesper Öqvist - 2018-01-03

Do not generate duplicate enum switch maps

This fixes an issue where duplicate implicit fields/methods were
generated for enum switch maps.

To fix this, the enumIndices() attribute was moved from SwitchStmt
to TypeDecl.

fixes #263 (bitbucket)


→ <<cset 5c20689936d5>>
