# Lambda expression causes unused class to be generated

**Status:** resolved

*ExtendJ 8.0.1-218-g7c6c778 Java SE 8*

ExtendJ generates an extra, unused, classfile.

Test code:

```java
import java.util.*;

public class M28 {

  public int field = 3;

  public interface A {
    public B m(int i);
  }

  public interface B {
    public boolean m();
  }

  public A getA() {
    return field -> () -> {
      return this.field == field;
    };
  }

  public static void main(String[] arg) {
    M28 t = new M28();
    A a = t.getA();
  }
}
```

Expected result: No unused classfiles are generated.

Actual result:

The following classfiles are generated when compiling the test code with ExtendJ: `M28.class`, `M28$A.class`, `M28$B.class`, `M28$1.class`, `M28$1$2.class`, `M28$3.class`.
The classfile `M28$3.class` is not reachable from `M28.class` and should not be generated.

## Comments

### Jesper Öqvist - 2017-12-06

Avoid redundant generated lambda classes

fixes #222


→ <<cset fec29acf5a00>>
