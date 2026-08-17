// Test type inference with a type argument substituted into capture variable bounds.
// .result=COMPILE_PASS
public class Test {
  <Si, P extends Si, S extends P> void g(Elaine<Si, P, S> c) { }

  void test(Elaine<Number, ?, ?> c) {
    // The upper bound of the capture of the first wildcard is Number here.
    g(c);
  }
}

class Elaine<Na, Mg extends Na, Al extends Mg> { }
