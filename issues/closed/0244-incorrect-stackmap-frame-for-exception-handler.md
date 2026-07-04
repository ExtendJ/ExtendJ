# Incorrect Stackmap Frame for exception handler

**Status:** resolved

*ExtendJ 8.0.1-242-g39743d8 Java SE 8*

When creating an exception handler stack frame, all basic blocks that can jump to the handler are not included in the stack frame computation. Only the first block in the exception range is used to construct the stack frame. This leads to incorrect stackmap frames for this test:

```java
public class Test {
  public static void main(String[] args) {
    Object lock = new Object();

    {
      int z = 1;
    }
    try {
      {
        int w = 2;
        if (args.length > 0) {
          w += 1;
        }
      }

      synchronized (lock) {
        return;
      }
    } catch (Exception e) {
      System.out.println("Exception:" + e.getMessage());
    }
  }
}
```

Expected result: should run without problem.

Actual result: runtime error:

```
    [junit] [FAIL] runTest[codegen/smf_02p](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Error: A JNI error has occurred, please check your installation and try again
    [junit] Exception in thread "main" java.lang.VerifyError: Stack map does not match the one at exception handler 34
    [junit] Exception Details:
    [junit]   Location:
    [junit]     Test.main([Ljava/lang/String;)V @34: astore_2
    [junit]   Reason:
    [junit]     Type 'java/lang/Object' (current frame, locals[2]) is not assignable to integer (stack map, locals[2])
    [junit]   Current Frame:
    [junit]     bci: @25
    [junit]     flags: { }
    [junit]     locals: { '[Ljava/lang/String;', 'java/lang/Object', 'java/lang/Object' }
    [junit]     stack: { 'java/lang/Exception' }
    [junit]   Stackmap Frame:
    [junit]     bci: @34
    [junit]     flags: { }
    [junit]     locals: { '[Ljava/lang/String;', 'java/lang/Object', integer }
    [junit]     stack: { 'java/lang/Exception' }
```

## Comments

### Jesper Öqvist - 2017-12-17

Fix errors in exception stackmap construction

Fixed two problems in exception handler stackmap frame construction:

* All basic blocks in the exception frame are now used to build the
  exception handler stack frame, instead of just the first one.
* The exception handler stack frame is now always cleared, with only the
  exception type on the stack.

fixes #244 (bitbucket)


→ <<cset 89c6ce2aa72b>>
