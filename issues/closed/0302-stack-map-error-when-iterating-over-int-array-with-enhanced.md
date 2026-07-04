# Stack map error when iterating over int array with enhanced for-loop

**Status:** resolved

*ExtendJ 8.1.1-25-gf6b42914 Java SE 8*

ExtendJ generates bad stack map frames for this program:

```test
public class F1 {
  public static void main(String[] args) {
    int[] x = { 1, 2, 3, };
    long last = 0;
    for (long i : x) {
      int pow = 3;
      last = i;
    }
    System.out.println(last);
  }
}
```

Compiling and running the program results in this error:

```
Exception in thread "main" java.lang.VerifyError: Bad local variable type
Exception Details:
  Location:
    F1.main([Ljava/lang/String;)V @44: lload
  Reason:
    Type top (current frame, locals[6]) is not assignable to long
  Current Frame:
    bci: @44
    flags: { }
    locals: { '[Ljava/lang/String;', '[I', long, long_2nd, '[I', integer, top, integer }
    stack: { }
```

## Comments

### Jesper Öqvist - 2018-03-19

This error is caused by the `pow` variable getting an incorrect local variable index, computed by `VariableDeclarator.localNum()`. The problem is probably that the enhanced-for statement does not correctly increase the local number count when the loop variable has type long/double.

### Jesper Öqvist - 2018-03-19

Here is a modified test that is not as likely to be fixed by dead code elimination:

```java
public class Test {
  public static void main(String[] args) {
    int[] x = { 12321 };
    long last = 0;
    for (long i : x) {
      int pow = (int)i;
      System.out.println(i);
    }
  }
}
```

### Jesper Öqvist - 2018-03-19

The fault is at line 42 in `java5/backend/EnhancedForCodegen.jrag`:

```java
  eq EnhancedForStmt.getStmt().localNum() =
      getVariableDecl().localNum() + getTypeAccess().type().size();
```

The `variableSize()` method should be called, not `size()`.

### Jesper Öqvist - 2018-03-19

The fault can be traced back all the way to commit 15b443ec5d01cf0cc22383c0fc7bd47411443ebc by Torbjörn.

### Jesper Öqvist - 2018-03-19

Fix incorrect local index inside for-each

The SimpleSet.size() attribute was incorrectly used instead of
VariableDeclarator.size(), causing incorrect local variable indices inside a
for-each loop with a long or double loop variable.

fixes #302 (bitbucket


→ <<cset ba7e55fd12ab>>
