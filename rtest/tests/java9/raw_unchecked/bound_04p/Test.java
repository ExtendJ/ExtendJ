// The other direction of bound_01f. Unchecked conversion is implied by
// incorporating the raw argument's bound ‹Seq <: α_L› with the declared bound
// ‹α_L <: Seq<α_U>›.
//
// From Java 9 onwards the invocation is applicable
// only by unchecked conversion, so the target type does not constrain the
// inference and the result type is the erasure. Without that,
// ‹Seq<α_U> → Seq<A>› would force α_U = A and contradict the lower bound B from
// the second argument, making the invocation inapplicable which is what
// Java 8 reports here.
// .result: COMPILE_PASS
public class Test {
  static <L extends Seq<U>, U> Seq<U> pair(L l, U u) {
    return l;
  }

  @SuppressWarnings("unchecked")
  void m(Seq raw) {
    Seq<A> s = pair(raw, new B());
  }
}

interface Seq<E> { }

class A { }

class B { }
