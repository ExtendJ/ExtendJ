# Method with a raw argument is not applicable by unchecked conversion

*ExtendJ 11.0.0-g25eca834 Java SE 8*

A raw argument makes unchecked conversion necessary for a generic method to be
applicable. ExtendJ reports the method as inapplicable when
another argument constrains the same inference variable.

```java
import java.util.List;

public class Test {
  void test(List raw, List<String> typed) {
    both(raw, typed);
  }

  static <X> void both(List<X> a, List<X> b) {
  }
}
```

Expected result: should compile with an unchecked warning.

Actual result:

```
error: no method named both(java.util.List, java.util.List<java.lang.String>) in Test matches.
  However, there is a method both(java.util.List<X>, java.util.List<X>)
```

`both(raw, raw)` and `single(raw)` for `static <X> void single(List<X> a)` are
accepted, so a raw argument on its own works. It is effectively the second argument
which also constrains `X` that makes the invocation inapplicable.

Issue 345 fixed the result type of an invocation that needs unchecked
conversion. This is the applicability of such an invocation, which is decided
before the result type is computed.

See regression test `rtest/tests/ti/raw_unchecked_06p`.
