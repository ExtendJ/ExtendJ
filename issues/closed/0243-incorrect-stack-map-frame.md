# Incorrect Stack Map Frame

**Status:** resolved

*ExtendJ 8.0.1-240-g3d86145 Java SE 8*

ExtendJ computes incorrect stack map frames for this test:

```java
// Test an error in stack map frame computation caused by
// wide types not being handled correctly.
public class Test {
  public static void main(String[] args) {
    {
      int i = 32394;
      int[] ia = { 1, 2 };
      int[] ia2 = { 1, 2 };
      if (i != 32394) return;
      System.out.print("1");
    }
    {
      long[] la = { 1l, 2l };
      long l = 6864468644l;
      if (l != 6864468644l) return;
      System.out.print("2");
    }
    {
      float f = .9f;
      float[] fa = { Float.NaN, Float.NaN };
      if (f != .9f) return;
      System.out.print("3");
    }
  }
}
```

Expected result: should print "123"

Actual result: fails to run with the following error:

```
    [junit] [FAIL] runTest[codegen/smf_01p](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Error: A JNI error has occurred, please check your installation and try again
    [junit] Exception in thread "main" java.lang.VerifyError: Inconsistent stackmap frames at branch target 104
    [junit] Exception Details:
    [junit]   Location:
    [junit]     Test.main([Ljava/lang/String;)V @104: getstatic
    [junit]   Reason:
    [junit]     Type top (current frame, locals[3]) is not assignable to '[I' (stack map, locals[3])
    [junit]   Current Frame:
    [junit]     bci: @100
    [junit]     flags: { }
    [junit]     locals: { '[Ljava/lang/String;', float, '[F', top }
    [junit]     stack: { integer }
    [junit]   Stackmap Frame:
    [junit]     bci: @104
    [junit]     flags: { }
    [junit]     locals: { '[Ljava/lang/String;', float, '[F', '[I' }
    [junit]     stack: { }
```

## Comments

### Jesper Öqvist - 2017-12-17

Fix error in stackmap frame building

Wide types were not handled correctly when updating local variable slots.
There should always be a TOP entry after a wide type in the local variable map.

fixes #243 (bitbucket)


→ <<cset 39743d8156d0>>
