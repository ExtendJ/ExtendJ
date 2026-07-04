# Broken constructor signature for anonymous class extending inner class

**Status:** resolved

*ExtendJ 8.0.1-235-g5497af4 Java SE 8*

Broken bytecode is generated for this test case:

```java
public final class Ju4 {

  public static void main(String[] args) {
    new Ju4().run();
  }

  private abstract class AbstractTest {
    public abstract void test();
  }

  public void run() {
    new AbstractTest() {
      public void test() {
        System.out.println("x marks the spot");
      }
    }.test();
  }
}
```

Expected result: should print "x marks the spot".

Actual result:

```
Exception in thread "main" java.lang.ClassFormatError: Arguments can't fit into locals in class file Ju4$1
        at java.lang.ClassLoader.defineClass1(Native Method)
        at java.lang.ClassLoader.defineClass(ClassLoader.java:763)
        at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
        at java.net.URLClassLoader.defineClass(URLClassLoader.java:467)
        at java.net.URLClassLoader.access$100(URLClassLoader.java:73)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:368)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:362)
        at java.security.AccessController.doPrivileged(Native Method)
        at java.net.URLClassLoader.findClass(URLClassLoader.java:361)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
        at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:338)
        at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
        at Ju4.run(Ju4.java:12)
        at Ju4.main(Ju4.java:4)
```

Relevant bytecode:

```
  Ju4$1(Ju4, Ju4) throws ;
    flags:
    Code:
      stack=3, locals=2, args_size=3
         0: aload_0
         1: aload_2
         2: aconst_null
         3: invokespecial #14                 // Method Ju4$AbstractTest."<init>":(LJu4;LJu4$AbstractTest;)V
         6: aload_0
         7: aload_1
         8: putfield      #16                 // Field this$0:LJu4;
        11: return
      LineNumberTable:
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
               0      12     0  this   LJu4$1;
               0      12     1 this$0   LJu4;
    Exceptions:
      throws
```

The enclosing class instance parameter is incorrectly duplicated.

## Comments

### Jesper Öqvist - 2017-12-15

Add missing super enclosing variable entry

Constructor bytecode generation did not include local variable entries for the
supertype enclosing instance.

fixes #234 (bitbucket)


→ <<cset 3d8614512ab5>>
