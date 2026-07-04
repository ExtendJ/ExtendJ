# Stackmap frame error in constructor

**Status:** resolved

*ExtendJ 8.0.1-243-g89c6ce2 Java SE 8*

ExtendJ generates incorrect stackmap frames for the default constructor in the following test:

```java
public class Test {
  int sum;

  Test() {
    this(0x523, 0x941, 0x22);
    if (sum == 3718) {
      System.out.println("pass");
    }
  }

  Test(int x) {
    sum = x;
  }

  Test(int x, int y) {
    this(x);
    sum += y;
  }

  Test(int x, int y, int z) {
    this(x, y);
    sum += z;
  }

  public static void main(String[] args) {
    new Test();
  }
}
```

Expected result: should print "pass".

Actual result: runtime error:

```
    [junit] [FAIL] runTest[codegen/smf_03p](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Error: A JNI error has occurred, please check your installation and try again
    [junit] Exception in thread "main" java.lang.VerifyError: Inconsistent stackmap frames at branch target 30
    [junit] Exception Details:
    [junit]   Location:
    [junit]     Test.<init>()V @30: return
    [junit]   Reason:
    [junit]     Type 'Test' (current frame, locals[0]) is not assignable to uninitializedThis (stack map, locals[0])
    [junit]   Current Frame:
    [junit]     bci: @19
    [junit]     flags: { }
    [junit]     locals: { 'Test' }
    [junit]     stack: { integer, integer }
    [junit]   Stackmap Frame:
    [junit]     bci: @30
    [junit]     flags: { flagThisUninit }
    [junit]     locals: { uninitializedThis }
    [junit]     stack: { }
```

## Comments

### Jesper Öqvist - 2017-12-17

Fix error in constructor stackmap construction

The this reference was not always marked as initialized in constructors.

fixes #245 (bitbucket)


→ <<cset cf8e8d95c006>>
