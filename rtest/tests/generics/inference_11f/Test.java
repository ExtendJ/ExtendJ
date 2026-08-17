// Test that inference fails for a reversed capture variable bound dependency.
// .result=COMPILE_FAIL
public class Test {
  <K, Ca extends K> void g(Kramer<Ca, K> c) { }

  void test(Kramer<?, ?> c) {
    g(c); // Error: the first capture variable is not a subtype of the second.
  }
}

class Kramer<Cl, Ar extends Cl> { }
