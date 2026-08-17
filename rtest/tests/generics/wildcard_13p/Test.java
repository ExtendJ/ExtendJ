// Test the lower bound of a capture-converted type argument.
// .result=COMPILE_PASS
public class Test {
  <Cr extends Integer> void put(Bania<? super Integer> d, Cr w) {
    // Cr is a subtype of Integer, the lower bound of the captured wildcard.
    d.add(w);
  }
}

class Bania<V> {
  void add(V x) { }
}
