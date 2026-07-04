# Code generation crashes from unplaced label in simple try-finally statement

**Status:** resolved

_ExtendJ 8.1.2-49-g2b9c6bd1 Java SE 8_

Code generation crashes for the following input program:

```
public class Test {
  public static void main(String[] args) {
    System.out.println(m());
  }

  public static boolean m() {
    try {
      return true;
    } finally {
      return false;
    }
  }
}
```

Expected result: the program should compile and output “false”
Actual result: code generation crashes with the following error message:

```
    [junit] Error while processing tests/codegen/finally08/Test.java:8
    [junit] Fatal exception:
    [junit] java.lang.Error: Can not compute address of unplaced label (id: -1)
```

‌

## Comments

### Jesper Öqvist - 2022-06-29

Fix code generation error

Fixed error in merging unlabelled basic block with a block that was an
exception target. An unlabelled deleted block that is being merged with another
block is now given a new label.

fixes #311 (bitbucket)


→ <<cset 2f258ecda394>>
