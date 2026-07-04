# Missing boxing conversions when calling generic method

**Status:** resolved

*ExtendJ 8.0.1-233-gb805aab Java SE 8*

Test:

```java
public class Ju2 {
  public static void main(String[] args) {
    moo(0);
    moo(1);
    moo(true);
    moo(false);
    moo(true);
    moo("ok");
  }

  public static <T> void moo(T moo) {
    if (moo.equals(Boolean.TRUE)) {
      System.out.println("moo");
    }
  }
}
```

Expected result: should print "moo" twice.

Actual result:

```
Error: A JNI error has occurred, please check your installation and try again
Exception in thread "main" java.lang.VerifyError: Bad type on operand stack
Exception Details:
  Location:
    Ju2.main([Ljava/lang/String;)V @15: invokestatic
  Reason:
    Type integer (current frame, stack[0]) is not assignable to 'java/lang/Object'
  Current Frame:
    bci: @15
    flags: { }
    locals: { '[Ljava/lang/String;' }
    stack: { integer }
  Bytecode:
    0x0000000: 03b8 0018 b800 1c04 b800 18b8 001c 04b8
    0x0000010: 001c 03b8 001c 04b8 001c 121e c000 04b8
    0x0000020: 001c b1

        at java.lang.Class.getDeclaredMethods0(Native Method)
        at java.lang.Class.privateGetDeclaredMethods(Class.java:2701)
        at java.lang.Class.privateGetMethodRecursive(Class.java:3048)
        at java.lang.Class.getMethod0(Class.java:3018)
        at java.lang.Class.getMethod(Class.java:1784)
        at sun.launcher.LauncherHelper.validateMainClass(LauncherHelper.java:544)
        at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:526)
```

## Comments

### Jesper Öqvist - 2017-12-13

This seems to affect just the boolean values.

### Jesper Öqvist - 2017-12-13

Relevant attribute:

```java
  refine CodeGeneration void BooleanType.emitCastTo(CodeGeneration gen, TypeDecl type) {
    if (type.unboxed() == this || type.isObject()) {
      boxed().emitBoxingOperation(gen);
    }
  }
```

### Jesper Öqvist - 2017-12-13

The argument type to `emitCast` in this case is the type variable `T`, which is neither `java.lang.Boolean` or `java.lang.Object`.

### Jesper Öqvist - 2017-12-13

A much simpler test that should work is to test if the argument type to the cast is a primitive type.

### Jesper Öqvist - 2017-12-13

Fix too restrictive boxing condition for boolean

This makes boolean autoboxing conversion work when the target type is a type
variable.

fixes #232 (bitbucket)


→ <<cset 7a7bcbc89a2b>>
