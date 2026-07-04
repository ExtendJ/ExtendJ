# Bad type array size

**Status:** resolved

*ExtendJ 8.0.1-234-g7a7bcbc Java SE 8*

ExtendJ generates broken stack map frames for this test:

```java
public class Test {
  static boolean compare(long x) {
    long l = 733903704455L;
    return (l != x);
  }

  public static void main(String[] args) {
    System.out.println(compare(733903704455L));
  }
}
```

Expected result: should print "true".

Actual result: runtime error:

```
    [junit] [FAIL] runTest[run/cmp_ne_01](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Error: A JNI error has occurred, please check your installation and try again
    [junit] Exception in thread "main" java.lang.ClassFormatError: StackMapTable format error: bad type array size in method Test.compare(J)Z
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

Fix stack map frame generation issue

An incorrect number of assigned local slots was computed when creating a diff
stack frame.  This lead to an incorrect number of append_frame entries.

Also fixed an error in how local variable space was allocated. For wide types,
the second entry was sometimes not set to TOP. Wide types should instead always
be followed by TOP in the local variable map.

fixes #238 (bitbucket)


→ <<cset 49efefa08afd>>
