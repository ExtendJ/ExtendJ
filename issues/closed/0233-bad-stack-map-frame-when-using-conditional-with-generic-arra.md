# Bad stack map frame when using conditional with generic array type

**Status:** resolved

*ExtendJ 8.0.1-234-g7a7bcbc Java SE 8*

ExtendJ generates broken bytecode for this test:

```java
public class Test {
  public static void main(String[] args) {
    System.out.println(foo(false).length);
    System.out.println(foo(true).length);
  }

  static Class<?>[] foo(boolean b) {
    return b ? new Class<?>[10] : new Class[20];
  }
}
```

Expected result: it should produce the output 20, 10.

Actual result:

```
    [junit] [FAIL] runTest[generics/array_erasure_01p](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Error: A JNI error has occurred, please check your installation and try again
    [junit] Exception in thread "main" java.lang.VerifyError: Bad return type
    [junit] Exception Details:
    [junit]   Location:
    [junit]     Test.foo(Z)[Ljava/lang/Class; @17: areturn
    [junit]   Reason:
    [junit]     Type 'java/lang/Object' (current frame, stack[0]) is not assignable to '[Ljava/lang/Class;' (from method signature)
    [junit]   Current Frame:
    [junit]     bci: @17
    [junit]     flags: { }
    [junit]     locals: { integer }
    [junit]     stack: { 'java/lang/Object' }
    [junit]   Bytecode:
    [junit]     0x0000000: 1a99 000b 100a bd00 29a7 0008 1014 bd00
    [junit]     0x0000010: 29b0
    [junit]   Stackmap Table:
    [junit]     same_frame(@12)
    [junit]     same_locals_1_stack_item_frame(@17,Object[#4])
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

Fix error in verification type equality tests

Reference identity can't be used to compare verification types since different
type declarations can have the same verification type.  This adds the sameType
method to compare verification types.

fixes #233 (bitbucket)


→ <<cset fcd63a5af775>>
