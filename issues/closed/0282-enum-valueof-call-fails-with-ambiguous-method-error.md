# Enum.valueOf() call fails with ambiguous method error

**Status:** resolved

*ExtendJ 8.1.0-39-g9f0c2f1 Java SE 5*

```java
// Test using the Enum.valueOf(String) method on a standard library type.
import java.net.Proxy;
public class Test {
  public static void main(String[] args) {
    System.out.println(Proxy.Type.valueOf("HTTP"));
  }
}
```

Expected result: should compile.

Actual result: fails with this error:

```
    [junit] [FAIL] runTest[enum/valueof_02p](tests.extendj.TestJava7)
    [junit] Compilation failed when expected to pass:
    [junit] tests/enum/valueof_02p/Test.java:5: error: no method named println(Unknown) in java.io.PrintStream matches. However, there is a method println(boolean)
    [junit] tests/enum/valueof_02p/Test.java:5: error: several most specific methods for valueOf("HTTP")
    [junit]     valueOf(java.lang.String) in java.net.Proxy.Type
    [junit]     valueOf(java.lang.String) in java.net.Proxy.Type
```

## Comments

### Jesper Öqvist - 2018-01-08

Fix duplicate implicit Enum methods error

This fixes an error where Enum types that were parsed from bytecode received
duplicate implicit method declarations (one from bytecode, one added
implicitly).

fixes #282 (bitbucket)


→ <<cset 660b9e43bd0e>>
