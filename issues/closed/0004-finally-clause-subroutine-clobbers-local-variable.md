# Finally clause subroutine clobbers local variable

**Status:** resolved

Trying to bootstrap JastAddJ `7.1.1-29-g550f24d Java SE 7`, then run the bootstrapped `jastaddj.jar` produces the following error:

````
Exception in thread "main" java.lang.VerifyError: (class: AST/Frontend, method: process signature: ([Ljava/lang/String;LAST/BytecodeReader;LAST/JavaParser;)Z) Register 9 contains wrong type
	at java.lang.Class.getDeclaredMethods0(Native Method)
	at java.lang.Class.privateGetDeclaredMethods(Class.java:2451)
	at java.lang.Class.getMethod0(Class.java:2694)
	at java.lang.Class.getMethod(Class.java:1622)
	at sun.launcher.LauncherHelper.getMainMethod(LauncherHelper.java:494)
	at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:486)
````

## Comments

### Jesper Öqvist - 2013-05-28

Minimal test case that produces the same kind of error:

````java
public class Test {
	public static void main(String[] args) {
		System.out.println(m());
	}

	public static boolean m() {
		try {
			String x = new String();
			String s = new String();
			try {
				return true;
			} catch (Throwable t) {
				s.toString();
				return false;
			}
		} finally {
		}
	}
}
````

When I compile that with `JastAddJ 7.1.1-29-g550f24d Java SE 7` and try to run it I get the following error:

````
Exception in thread "main" java.lang.VerifyError: (class: Test, method: m signature: ()Z) Register 2 contains wrong type
	at java.lang.Class.getDeclaredMethods0(Native Method)
	at java.lang.Class.privateGetDeclaredMethods(Class.java:2451)
	at java.lang.Class.getMethod0(Class.java:2694)
	at java.lang.Class.getMethod(Class.java:1622)
	at sun.launcher.LauncherHelper.getMainMethod(LauncherHelper.java:494)
	at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:486)
````

Here is the disassembly of the faulty bytecode:

````
  public static boolean m() throws ;
    Code:
       0: new           #33                 // class java/lang/String
       3: dup
       4: invokespecial #37                 // Method java/lang/String."<init>":()V
       7: astore_1
       8: new           #33                 // class java/lang/String
      11: dup
      12: invokespecial #37                 // Method java/lang/String."<init>":()V
      15: astore_2
      16: iconst_1
      17: istore_0
      18: jsr           42
      21: iload_0
      22: ireturn
      23: astore_3
      24: aload_2
      25: invokevirtual #42                 // Method java/lang/String.toString:()Ljava/lang/String;
      28: pop
      29: iconst_0
      30: istore_0
      31: jsr           42
      34: iload_0
      35: ireturn
      36: astore_1
      37: jsr           42
      40: aload_1
      41: athrow
      42: astore_2
      43: ret           2
    Exception table:
       from    to  target type
          16    23    23   Class java/lang/Throwable
           0    36    36   any
````

### Jesper Öqvist - 2013-05-29

First I thought the problem was that `astore 2` would try to read an integer from the stack as a reference, but that is not the case.

The problem is probably that the first `jsr` clobbers local variable 2 so that the `aload_2` at address 23 reads the subroutine return address rather than the second String reference that was stored to the same local variable at address 15.

### Jesper Öqvist - 2013-05-29

More precise title.

### Jesper Öqvist - 2013-05-29

More concise description.

### Jesper Öqvist - 2013-05-30

The simple way to fix this is to just allocate one extra local for the finally clause before allocating locals for the try statement body.

### Jesper Öqvist - 2013-05-30

Fixed localNum problem for finally clause

fixes #4


→ <<cset 209d65cbe9d7>>
