# Broken bytecode when using generic container to access int array

**Status:** resolved

*ExtendJ 8.1.0-21-g42ca049 Java SE 7*

ExtendJ generates unexecutable bytecode for the following program:

```java
// Test generic container with primitive array argument.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    int[] values = new int[100];
    for (int i = 0; i < 100; ++i) {
      values[i] = (i * i * 255 + i * 6) % 100;
    }
    run(values);
  }

  static void run(int[] values) {
    Container<int[]> con = new Container<int[]>(values);
    if (con.get()[43] != 53 || con.get()[44] != 44) {
      System.out.println("wrong values");
    }
  }

}

class Container<T> {
  private final T value;

  public Container(T value) {
    this.value = value;
  }

  T get() {
    return value;
  }
}
```

Expected result: should be executable.

Actual result: fails to run:

```
    [junit] [FAIL] runTest[run/generics/container_04](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Exception in thread "main" java.lang.VerifyError: Bad type on operand stack in iaload
    [junit] Exception Details:
    [junit]   Location:
    [junit]     Test.run([I)V @15: iaload
    [junit]   Reason:
    [junit]     Type 'java/lang/Object' (current frame, stack[0]) is not assignable to '[I'
    [junit]   Current Frame:
    [junit]     bci: @15
    [junit]     flags: { }
    [junit]     locals: { '[I', 'Container' }
    [junit]     stack: { 'java/lang/Object', integer }
    ...
```

## Comments

### Jesper Öqvist - 2018-01-05

Add missing array access casting conversions

This fixes problems in code generation for array accesses via generic types.
It may be necessary to cast a generic type to the expected array type
before generating an array access instruction.

This also fixes incorrect equations for the attribute Expr.erasedType().

fixes #273 (bitbucket)


→ <<cset 4f6c910ee290>>
