# Local variables can be declared multiple times

**Status:** resolved

Hi,

thanks for creating and publishing ExtendJ! This framework is great for developing extensions to Java that affect both Java syntax and class files. While trying to understand the framework I discovered the following issue that I'd like to share:

Local variables can be declared (and initialised) multiple times in a method or constructor. Take the example `Test.java`:

```java
public class Test {
   public Test() {
      int a = 0;
      int a = 1;
   }
}
```

After compiling with `java -jar extendj.jar Test.java` (ExtendJ 8.0.1-126-ga069f0d Java SE 8), we can inspect the class file and get the following:

```
# javap -l Test.class
Compiled from "Test.java"
public class Test {
  public Test() throws ;
    LineNumberTable:
      line 3: 4
      line 4: 6
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
          0       9     0  this   LTest;
          4       4     1     a   I
          6       2     2     a   I
}
```

Note that compiling did not indicate any problems and that the LocalVariableTable now contains two distinct variables with an identical name.

For my project this is not an actual issue, as (for now) I can choose examples arbitrarily - but I thought you might be interested :) Feel free to contact me for any follow-up questions. Thanks again for this awesome project!

## Comments

### Jan Dageförde - 2016-07-28

Digging a little deeper (but still just scratching the surface) I noticed that this does **not** hold for fields; they may only be declared once:
```java
public class Test2 {
        private int b = 0;
        private int b = 0;
}
```

In this case, invoking `java -jar extendj.jar Test2.java` results in the following error output:
```
# java -jar extendj.jar Test2.java
Test2.java:2: error: field named b is multiply declared in type Test
Test2.java:3: error: field named b is multiply declared in type Test
```

### Jesper Öqvist - 2016-07-29

This bug is very surprising. Thank you for noticing and reporting it!

I wrote a small regression test to confirm the bug, then I tried it on some older versions of ExtendJ and the bug seems to have existed for a long time - at least since 2013.

### Jesper Öqvist - 2016-07-29

Fix duplicate variable check

Fixed duplicate variable declaration error check for local variables.

Modified the error messages for parameter and catch declarations
shadowed by local variables.

fixes #179 (bitbucket)


→ <<cset ec7b7a0505cf>>
