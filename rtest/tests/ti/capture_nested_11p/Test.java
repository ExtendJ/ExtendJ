// Test lifting a bounded type variable of a nested invocation with a wildcard result.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <B extends Horn> Renhorn<B, ?> inre(B b);
  abstract <Y> Horn yttre(Renhorn<? extends Horn, Y> g);

  // B and the captured type argument of the result are equal and both
  // bounded by Horn when lifted into the outer inference.
  Horn horn = yttre(inre(new Skohorn()));
}

interface Horn { }
class Skohorn implements Horn { }
interface Renhorn<S, T> { }
