# Bad stack map frame when using conditional with different array types

**Status:** resolved

*ExtendJ 8.0.1-234-g7a7bcbc Java SE 8*

When building Stack Map Frames, ExtendJ does not compute the common supertype of two array types correctly. In this case, the compiler should compute the type `[Ljava/lang/Number` as the common supertype of `[Ljava/lang/Number` and `[Ljava/lang/Float` in `stack[2]` of the merge frame after the conditional expression. Instead, ExtendJ computes `java/lang/Object` as the common supertype.

```java
// Test that the correct verification types are generated for a conditional
// expression with two different array types.
public class Test {
  public static void main(String[] args) {
    new Test().run(true);
    new Test().run(false);
  }

  void run(boolean b) {
    Float[] floats = new Float[3];
    System.out.println(process(b ? new Number[20] : floats));
  }

  int process(Object o) {
    return -1;
  }

  int process(Number[] n) {
    return 20;
  }
}
```

Expected result: should print 20, 20.

Actual result: fails to run with this error:

```
    [junit] [FAIL] runTest[run/conditional_04](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Error: A JNI error has occurred, please check your installation and try again
    [junit] Exception in thread "main" java.lang.VerifyError: Bad type on operand stack
    [junit] Exception Details:
    [junit]   Location:
    [junit]     Test.run(Z)V @22: invokevirtual
    [junit]   Reason:
    [junit]     Type 'java/lang/Object' (current frame, stack[2]) is not assignable to '[Ljava/lang/Number;'
    [junit]   Current Frame:
    [junit]     bci: @22
    [junit]     flags: { }
    [junit]     locals: { 'Test', integer, '[Ljava/lang/Float;' }
    [junit]     stack: { 'java/io/PrintStream', 'Test', 'java/lang/Object' }
    [junit]   Bytecode:
    [junit]     0x0000000: 06bd 001e 4db2 0024 2a1b 9900 0b10 14bd
    [junit]     0x0000010: 0026 a700 042c b600 2ab6 0030 b1
    [junit]   Stackmap Table:
    [junit]     full_frame(@21,{Object[#2],Integer,Object[#50]},{Object[#44],Object[#2]})
    [junit]     full_frame(@22,{Object[#2],Integer,Object[#50]},{Object[#44],Object[#2],Object[#4]})
    [junit]
    [junit]     at java.lang.Class.getDeclaredMethods0(Native Method)
    [junit]     at java.lang.Class.privateGetDeclaredMethods(Class.java:2701)
    [junit]     at java.lang.Class.privateGetMethodRecursive(Class.java:3048)
    [junit]     at java.lang.Class.getMethod0(Class.java:3018)
    [junit]     at java.lang.Class.getMethod(Class.java:1784)
    [junit]     at sun.launcher.LauncherHelper.validateMainClass(LauncherHelper.java:544)
    [junit]     at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:526)]>
```

## Comments

### Jesper Öqvist - 2017-12-15

Fix error in array verification type construction

Common supertypes for array verification types were not computed correctly,
leading to too loose verification types in stack map frames.

fixes #236 (bitbucket)


→ <<cset 487581b77faf>>
