# Broken bytecode in nested try statements

**Status:** resolved

*ExtendJ 8.0.1-239-g7ed3ac9 Java SE 8*

There is an error in deleting unreachable blocks during code generation that corrupts an instruction after a deleted block.

```java
public class N6 {

  public static void main(String[] args) {
    run(1);
    run(2);
  }

  static void run(int i) {
    int target = 0;
Outer:
    {
      target |= 1;
      try {
        try {
          target |= 2;
          if (i < 2)
            break Outer;
          target |= 4;
        } finally {
          target |= 8;
          break Outer;
        }
      } finally {
        target |= 16;
      }
    }
    target |= 32;
    System.out.println(Integer.toString(target, 2));
  }

}
```

Expected result: should print "111011", "111111"

Actual result:

```
Exception in thread "main" java.lang.VerifyError: Operand stack underflow
Exception Details:
  Location:
    N6.run(I)V @30: laload
  Reason:
```

## Comments

### Jesper Öqvist - 2017-12-15

Avoid patching jumps in deleted blocks

It is not safe to patch a jump in a deleted block.  This adds a reference to
the source block for jumps so that they are not patched if the source block is
deleted.

fixes #241 (bitbucket)


→ <<cset 7a74d02ef487>>
