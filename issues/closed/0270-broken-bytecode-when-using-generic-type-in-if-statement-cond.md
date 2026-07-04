# Broken bytecode when using generic type in if-statement condition

**Status:** resolved

*ExtendJ 8.1.0-21-g42ca049 Java SE 7*

ExtendJ generates unexecutable bytecode for the following program:

```java
// Test if-statement with generic condition.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    run(204 + 1);
  }

  static void run(int value) {
    Container<Boolean> con = new Container<Boolean>(value % 100 == 4);
    if (con.get()) {
      System.out.println("should be false");
    }
    if (!con.get()) {
      return;
    }
    System.out.println("should be false");
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
    [junit] [FAIL] runTest[run/generics/container_01](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Exception in thread "main" java.lang.VerifyError: Bad type on operand stack
    [junit] Exception Details:
    [junit]   Location:
    [junit]     Test.run(I)V @28: invokevirtual
    [junit]   Reason:
    [junit]     Type 'java/lang/Object' (current frame, stack[0]) is not assignable to 'java/lang/Boolean'
    [junit]   Current Frame:
    [junit]     bci: @28
    [junit]     flags: { }
    [junit]     locals: { integer, 'Container' }
    [junit]     stack: { 'java/lang/Object' }
    ...
```

## Comments

### Jesper Öqvist - 2018-01-05

Fix error in conditional branch unboxing

If the expression in a conditional branch had a generic type, that does not
have java.lang.Boolean as its erasure, then the expression has to be cast to
java.lang.Boolean before unboxing to boolean.

fixes #270 (bitbucket)
fixes #271 (bitbucket)
fixes #272 (bitbucket)


→ <<cset 329e3d61a7ef>>
