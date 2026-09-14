// Test an array constructor reference with a wildcard parameterized element type.
// .result: COMPILE_PASS
public class Test {
  Wrinkler<Grandma<?>[]> nan = Grandma<? extends Object>[]::new;
}

interface Grandma<Q> { }
interface Wrinkler<A> { A eat(int cookies); }
