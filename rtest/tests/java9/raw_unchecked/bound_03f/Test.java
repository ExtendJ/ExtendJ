// As bound_01f, but the raw type comes from the result of an invocation rather
// than from a variable. From Java 9 onwards the result type of the invocation
// is the erasure of E (=Object).
// .result: COMPILE_FAIL
public class Test {
  static <C extends Bag<E>, E> E any(C c) {
    return c.any();
  }

  @SuppressWarnings("unchecked")
  static Bag raw() {
    return null;
  }

  void m() {
    Elem e = any(raw()); // E is not inferred as Elem.
  }
}

interface Bag<E> {
  E any();
}

class Elem { }
