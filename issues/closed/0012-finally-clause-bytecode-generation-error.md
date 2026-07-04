# Finally clause bytecode generation error

**Status:** resolved

Test case:

````java
public class Test {
        public static void main(String[] args) {
                try {
                } finally {
                        int [] b = new int [3];
                }
        }
}
````

Running the generated code produces a VerifyError:

````
Exception in thread "main" java.lang.VerifyError: (class: Test, method: main signature: ([Ljava/lang/String;)V) Expecting to find object on stack
        at java.lang.Class.getDeclaredMethods0(Native Method)
        at java.lang.Class.privateGetDeclaredMethods(Class.java:2451)
        at java.lang.Class.getMethod0(Class.java:2694)
        at java.lang.Class.getMethod(Class.java:1622)
        at sun.launcher.LauncherHelper.getMainMethod(LauncherHelper.java:494)
        at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:486)
````

## Comments

### Jesper Öqvist - 2013-06-02

This is caused by not allocating enough local indices for the finally clause and the resulting local variable collision.

The faulty bytecode:

````
  public static void main(java.lang.String[]) throws ;
    Code:
       0: goto          3
       3: jsr           15
       6: goto          22
       9: astore_2
      10: jsr           15
      13: aload_2
      14: athrow
      15: astore_1
      16: iconst_3
      17: newarray       int
      19: astore_2
      20: ret           1
      22: return
    Exception table:
       from    to  target type
           0     3     9   any
````

### Jesper Öqvist - 2013-06-02

Fixed localNum error for finally clause

fixes #12


→ <<cset 2f430028dd63>>
