# Stack map error for TWR statement with multiple resources

**Status:** resolved

*ExtendJ 8.1.0-46-g6dab868 Java SE 8*

```
public class Cy1 implements AutoCloseable {
  public static void main(String[] args) {
    try (Cy1 p0 = new Cy1();
        Cy1 parser = new Cy1()) {
      return;
    } catch (Throwable e) {
    }
  }

  public void close() { }
}
```

Expected result: the program should compile and execute without error.

Actual result: the compiled code fails to run:

```
Exception in thread "main" java.lang.VerifyError: Stack map does not match the one at exception handler 70
Exception Details:
  Location:
    Cy1.main([Ljava/lang/String;)V @70: astore_2
  Reason:
    Type top (current frame, locals[1]) is not assignable to 'Cy1' (stack map, locals[1])
  Current Frame:
    bci: @53
    flags: { }
    locals: { '[Ljava/lang/String;' }
    stack: { 'java/lang/Throwable' }
  Stackmap Frame:
    bci: @70
    flags: { }
    locals: { '[Ljava/lang/String;', 'Cy1' }
    stack: { 'java/lang/Throwable' }
```

## Comments

### Jesper Öqvist - 2018-01-11

Fix stack map construction error

fixes #288 (bitbucket)


→ <<cset ddbdc642983e>>
