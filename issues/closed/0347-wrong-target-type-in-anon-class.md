# Wrong target type inside anonymous class

**Status:** resolved

*ExtendJ 11.0.0-g25eca834 Java SE 8*

The `targetType` attribute is used in generic method inference to constrain
type variables in the generic method invocation.

When a target type is not available, the `targetType` attribute is supposed to
return `unknownType()`. However, it did not for cases where the generic method
invocation was inside an anonymous class expression which itself had a target
type. For example:

```java
import java.util.Collection;
import java.util.List;

public class Test {
  Runnable target = new Runnable() {
    public void run() {
      List<String> list = null;
      element(list);
    }
  };

  static <X> void element(Collection<X> c) {
  }
}
```

Expected result: compiles.

Actual result:

```
error: no method named element(java.util.List<java.lang.String>) in Test.#Anonymous matches.
  However, there is a method element(java.util.Collection<X>)
```

This error was solved simply by adding this missing equation:

```java
  eq BodyDecl.getChild().targetType() = unknownType();
```
