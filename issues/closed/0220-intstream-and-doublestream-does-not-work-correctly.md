# IntStream and DoubleStream does not work correctly

**Status:** resolved

In Program:

```java
import java.util.stream.*;
import java.util.*;

public class PQL {
	public static void main(String[] args) {
		Set<Integer> y;
		y = IntStream.range(0, 56).boxed().collect(Collectors.toSet());
	}
}
```

Expected result: It execute without error.

Actual result: When it executes we get the output:

```
Error: A JNI error has occurred, please check your installation and try again
Exception in thread "main" java.lang.VerifyError: (class: PQL, method: main signature: ([Ljava/lang/String;)V) Unable to pop operand off an empty stack
	at java.lang.Class.getDeclaredMethods0(Native Method)
	at java.lang.Class.privateGetDeclaredMethods(Class.java:2701)
	at java.lang.Class.privateGetMethodRecursive(Class.java:3048)
	at java.lang.Class.getMethod0(Class.java:3018)
	at java.lang.Class.getMethod(Class.java:1784)
	at sun.launcher.LauncherHelper.validateMainClass(LauncherHelper.java:544)
	at sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:526)
```

## Comments

### Jesper Öqvist - 2017-11-28

The problem here is that `invokeinterface` is generated for the call to `IntStream.range(int, int)` instead of `invokestatic`.

Here is a test case that demonstrates the core issue:

```java
// Interface methods can be static.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    I.foo();
  }
}

interface I {
  static void foo() {}
}
```

### Jesper Öqvist - 2017-11-28

Altering the bytecode generation so that `invokestatic` is generated instead does not fix this issue because bytecode version 49.0 can't use static methods in interfaces. We need to update to bytecode version 52.0 to support this, but that requires generating stack map frames which is currently not implemented and will take a lot of work  to fully implement (see issue #17).

### Jesper Öqvist - 2017-11-29

Add unsupported feature message (static interface methods)

ExtendJ generates an error message if a static interface method is called.
Static interface methods were added in Java 8, but they require bytecode
version 52.0 or higher to use.  Calling static interface methods is not
supported yet by ExtendJ because it generates bytecode version 49.0.

see #220 (bitbucket)


→ <<cset 85fe215542d5>>

### Jesper Öqvist - 2017-12-09

Code generation for static interface method calls

Implemented code generation for calling static interface methods.

fixes #220 (bitbucket)


→ <<cset 64c98bc8e5ed>>
