# Broken bytecode when using Java 8 return type inference

**Status:** resolved

*ExtendJ 8.0.1-229-g3f4f983 Java SE 8*

There is an error in code generation for the following test case:

```java
// This was adapted from a Stack Overflow question:
// https://stackoverflow.com/questions/40154690/java-8-type-inference-bug
// Both method calls should bind to the String argument method.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    method("");
    method(get());
  }

  static void method(String v) {
    System.out.println("String");
  }

  static void method(Object v) {
    System.out.println("Object");
  }

  interface I {
  }

  static <T extends I> T get() {
    return null;
  }
}
```

Expected result: no runtime problems.

Actual result: runtime exception:

```
    [junit] [FAIL] runTest[jsr335/inference/method_07p](tests.extendj.TestJava7)
    [junit] Error output files differ expected:<[]> but was:<[Error: A JNI error has occurred, please check your installation and try again
    [junit] Exception in thread "main" java.lang.NoClassDefFoundError: wildcards/& java/lang/String& Test/I
    [junit]     at java.lang.Class.getDeclaredMethods0(Native Method)
    [junit]     at java.lang.Class.privateGetDeclaredMethods(Class.java:2701)
    [junit]     at java.lang.Class.privateGetMethodRecursive(Class.java:3048)
    [junit]     at java.lang.Class.getMethod0(Class.java:3018)
    [junit]     at java.lang.Class.getMethod(Class.java:1784)
    [junit]     at sun.launcher.LauncherHelper.validateMainClass(LauncherHelper.java:544)
    [junit]     at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:526)
    [junit] Caused by: java.lang.ClassNotFoundException: wildcards.& java.lang.String& Test.I
    [junit]     at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
    [junit]     at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
    [junit]     at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:338)
    [junit]     at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
    [junit]     ... 7 more]>
```

## Comments

### Jesper Öqvist - 2017-12-12

Fixed description: the wrong code snippet was included.

### Jesper Öqvist - 2017-12-12

The method invocations are correct, but the checkcast generated before the second invocation is broken:

```
         0: ldc           #20                 // String
         2: invokestatic  #24                 // Method method:(Ljava/lang/String;)V
         5: invokestatic  #28                 // Method get:()LTest$I;
         8: checkcast     #30                 // class "wildcards/& java/lang/String& Test/I"
        11: invokestatic  #24                 // Method method:(Ljava/lang/String;)V
```

### Jesper Öqvist - 2017-12-13

Improve checkcast generation

Fixed several cases where redundant checkcast instructions could be generated.
This is done by moving the casting operation from where the expression value is
evaluated, to the context where it is used.

Also fixed incorrect casts by using the erased source type to decide if a cast
is needed.

Future work: look over casts in AssignExpr.createBCode, Unary.emitPostfix, and
Unary.emitPrefix.

fixes #230 (bitbucket)
fixes #229 (bitbucket)


→ <<cset b654375798b4>>

### Jesper Öqvist - 2017-12-13

Reverted the fix for this temporarily.

### Jesper Öqvist - 2017-12-19

Redesign typecasting code generation

Removed casting conversions directly after method invocation and field loading.
Instead, casting is now handled in the context where a field or method is used.

Added method Expr.emitCastTo(_, _) to handle type conversion from an expression
to an expected type.

Added method Expr.emitAssignConvTo(_, _) to handle assignment conversion from
an expression to an expected type.

Added attribute Expr.erasedType(), for computing the erased runtime type of an
expression.

fixes #229 (bitbucket)
fixes #230 (bitbucket)


→ <<cset 738074c49cbc>>
