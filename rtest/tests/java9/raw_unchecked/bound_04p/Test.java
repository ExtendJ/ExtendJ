// This test expects the faulty javac behavior.
// The test should actually fail according to the JLS.
//
// The constraints are
//
//   Seq <: αL         from the raw argument
//   αL <: Seq<αU>     from L's declared bound
//   -----
//   Seq <: Seq<αU>    by incorporation
//
// The last constraint should reduce to false according to §18.2.3.
// This is an open bug on the JDK bug tracker: https://bugs.openjdk.org/browse/JDK-8026527
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
