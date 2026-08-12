// Test inference constraint dependency cycle.
// The second lambda's only input variable A is already instantiated by the
// target type, but it still blocks on the first lambda's constraint mentioning
// A as an output variable,. The resulting cycle is broken by reducing the
// leftmost constraint, resolving B to Object before the second lambda can
// give it a lower bound.
// .result: COMPILE_FAIL
import java.util.List;

public class Test {
  static <A, B> List<A> m(Kanin<B, A> g, Kanin<A, B> f) { return null; }

  {
    List<String> r = m(b -> b.intValue() > 0 ? "miffy" : "kanin", a -> a.length());
  }
}

interface Kanin<In, Ut> {
  Ut apply(In in);
}
