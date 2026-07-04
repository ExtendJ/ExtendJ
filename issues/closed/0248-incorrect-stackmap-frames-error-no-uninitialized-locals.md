# Incorrect Stackmap Frames error: no uninitialized locals

**Status:** resolved

*ExtendJ 8.0.1-249-gfe3a489 Java SE 8*

The following test causes a runtime error due to incorrect stackmap frames:

```java
public class Test {
  public static void main(String[] args) {
    boolean b = args.length > 0;
    Binary t = new Binary(!b);
  }

  static class Binary {
    public Binary(boolean value) {
      System.out.println(value);
    }
  }

}
```

Expected result: should print "true"

Actual result: fails to run:

```
    [junit] [FAIL] runTest[codegen/smf_04p](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Error: A JNI error has occurred, please check your installation and try again
    [junit] Exception in thread "main" java.lang.VerifyError: Inconsistent stackmap frames at branch target 24
    [junit] Exception Details:
    [junit]   Location:
    [junit]     Test.main([Ljava/lang/String;)V @24: iconst_0
    [junit]   Reason:
    [junit]     Type uninitialized 12 (current frame, stack[0]) is not assignable to 'Test$Binary' (stack map, stack[0])
    [junit]   Current Frame:
    [junit]     bci: @17
    [junit]     flags: { }
    [junit]     locals: { '[Ljava/lang/String;', integer }
    [junit]     stack: { uninitialized 12, uninitialized 12, integer }
    [junit]   Stackmap Frame:
    [junit]     bci: @24
    [junit]     flags: { }
    [junit]     locals: { '[Ljava/lang/String;', integer }
    [junit]     stack: { 'Test$Binary', 'Test$Binary' }
```

## Comments

### Jesper Öqvist - 2017-12-18

Added test for this: `codegen/smf_04p`

### Jesper Öqvist - 2017-12-18

Track uninitialized references in stackmap

This adds tracking of uninitialized class references in stackmap frames.

fixes #248 (bitbucket)


→ <<cset ea2c7fcc4f0b>>
