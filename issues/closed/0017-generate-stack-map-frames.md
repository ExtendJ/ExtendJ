# Generate Stack Map Frames

**Status:** resolved

Generate stack map frames (Java 6 bytecode feature).

Stack map frames were added in bytecode version 50 (Java 6), and bytecode with version 51 or higher (Java 7+) must have stack map frames.

ExtendJ currently (8.0.1-215-g85fe215) still outputs bytecode version 49 (Java 5).

Some Java 8 features only work if the bytecode has version 52. This includes default interface methods and calling static interface methods. To support these features, ExtendJ must generate stack map frames.

## Comments

### Jesper Öqvist - 2017-12-01

A curious thing popped up while working on the SMF implementation: one full frame had the wrong offset and it caused the JVM to silently run the program without error, but the program did not complete as it should:

```
  public static void main(java.lang.String[]) throws ;
    flags: ACC_PUBLIC, ACC_STATIC
    Exceptions:
      throws
    Code:
      stack=2, locals=3, args_size=1
         0: aload_0
         1: arraylength
         2: iconst_0
         3: if_icmple     23
         6: sipush        10102
         9: invokestatic  #26                 // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        12: astore_1
        13: getstatic     #32                 // Field java/lang/System.out:Ljava/io/PrintStream;
        16: aload_1
        17: invokevirtual #38                 // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
        20: goto          33
        23: ldc           #41                 // String x
        25: astore_1
        26: getstatic     #32                 // Field java/lang/System.out:Ljava/io/PrintStream;
        29: aload_1
        30: invokevirtual #38                 // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
        33: return
      LineNumberTable:
        line 3: 0
        line 4: 6
        line 5: 13
        line 7: 23
        line 8: 26
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
               0      34     0  args   [Ljava/lang/String;
               6      14     1   foo   Ljava/lang/Object;
              23      10     1   bar   Ljava/lang/Object;
      StackMapTable: number_of_entries = 2
           frame_type = 23 /* same */
           frame_type = 255 /* full_frame */
          offset_delta = 33
          locals = [ top, class java/lang/Object ]
          stack = []
```

The prints "x" if the full frame offset delta is adjusted to 9.

### Jesper Öqvist - 2017-12-09

Stack Map Frames

Added stack map frames support for Java 6+ bytecode generation.

fixes #17 (bitbucket)


→ <<cset 21b8c093449a>>
