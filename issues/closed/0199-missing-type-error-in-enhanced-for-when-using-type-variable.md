# Missing type error in enhanced for when using type variable as iterated type

**Status:** resolved

*ExtendJ 8.0.1-166-gb66a11e Java SE 8*

ExtendJ does not handle type analysis of the iterated expression in an enhanced for correctly.

The following should generate a type error:

```java
// .result=COMPILE_FAIL
import java.util.List;

public class Test {
  public <T extends List<Integer>> String join(T list) {
    String str = "";
    for (String a : list) {
      str += a;
    }
    return str;
  }
}
```

The attribute `TypeDecl.iterableElementType()` is used to compute the element type of the expression in the enhanced for, however it does not compute the correct type when the type of the expression is represented by a type variable.

## Comments

### Jesper Öqvist - 2017-04-21

Improve type analysis for enhanced for

Added equation for iterableElementType() in TypeVariable.

fixes #199 (bitbucket)


→ <<cset 94430cebedb3>>
