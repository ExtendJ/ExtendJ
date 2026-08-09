// The raw argument gives the bound ‹Seq <: α_L› and the declared bound of L
// gives ‹α_L <: Seq<α_U>›. Incorporating the pair implies ‹Seq <: Seq<α_U>›,
// which holds only by unchecked conversion.
//
// Java 9 onwards counts that unchecked conversion as being necessary for the
// method to be applicable, so the result type of the invocation is the erasure
// of the declared result type (Object in this case). Java 8 counts only
// the unchecked conversion needed to reduce an argument compatibility
// constraint ‹ei → Fi›, and infers U from the target type instead (Elem in this case).
// .result: COMPILE_FAIL
public class Test {
  static <L extends Seq<U>, U> U head(L l) {
    return l.head();
  }

  @SuppressWarnings("unchecked")
  void m(Seq raw) {
    Elem e = head(raw); // U is not inferred as Elem.
  }
}

interface Seq<E> {
  E head();
}

class Elem { }
