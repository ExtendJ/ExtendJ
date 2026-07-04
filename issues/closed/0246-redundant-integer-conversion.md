# Redundant integer conversion

**Status:** resolved

*ExtendJ 8.0.1-244-gcf8e8d9 Java SE 8*

ExtendJ generates redundant integer conversions, like i2c in this test:

```
public class Test {
  public static void main(String[] args) {
    char c = 1234;
  }
}
```

Expected result: the above test should generate the following bytecode:

```
         0: sipush        1234
         3: istore_1
         4: return
```


Actual result: an unnecessary i2c instruction is generated:

```
         0: sipush        1234
         3: i2c
         4: istore_1
         5: return
```

## Comments

### Jesper Öqvist - 2017-12-18

Added test for this issue: `codegen/conv_01p`.

### Jesper Öqvist - 2017-12-18

Revert "Remove TypeDecl.emitAssignConvTo(_, _)"

This reverts commit 3a88c80a6350a03cea6c9eb09a18f0724f6dcda1.

Removing emitAssignConvTo caused unnecessary integer conversions (i2c, i2s,
etc.) to be generated.

fixes #246 (bitbucket)


→ <<cset a4c8c1c8c445>>
