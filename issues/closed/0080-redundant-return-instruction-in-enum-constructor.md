# Redundant RETURN instruction in Enum constructor

**Status:** resolved

The bytecode generated for Enum constructors has a redundant RETURN instruction at the end.

Minimal test case:

```java
enum A5 {
    A5_1 { int value = -1; }
}
```

The following bytecode is generated (note the double RETURN):

```
  public A5(java.lang.String, int, A5$2) throws ;
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=5, args_size=4
         0: aload_0
         1: aload_1
         2: iload_2
         3: invokespecial #42                 // Method "<init>":(Ljava/lang/String;I)V
         6: return
         7: return
      LineNumberTable:
        line 0: 6
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
               0       8     0  this   LA5;
               0       8     1    p0   Ljava/lang/String;
               0       8     2    p1   I
               0       8     3    p2   LA5$2;
    Exceptions:
      throws
    Synthetic: true
```

This is a minor bytecode problem, but a blocker for stack map frames.

## Comments

### Jesper Öqvist - 2014-09-15

Removed redundant RETURN instruction generation

fixes issue #80 (bitbucket)


→ <<cset ef1d15a23310>>

### Jesper Öqvist - 2017-12-11

Removing milestone: java8 (automated comment)
