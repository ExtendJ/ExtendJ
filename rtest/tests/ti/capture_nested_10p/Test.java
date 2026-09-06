// Test multiple nested bounds lifting.
// .result: COMPILE_PASS
public class Test {
  private Graph<String, Value> results;

  void disperse(View<Graph.Edge<String, Value>> view) {
    view.transmit(Slicer.slice(Graph.Edge::source, entry -> entry.sink()));
  }
}

interface Graph<K,V> {
  interface Edge<Kn,Vn> {
    Kn source();
    Vn sink();
  }
}
interface Value { }
interface View<T> {
  <R, A> void transmit(Slice<? super T, A, R> r);
}
interface Move<Alpha, Beta> {
  Beta move(Alpha alpha);
}
interface Slice<T,A,R> { }
class Slicer {
  static <Z, Theta, Phi> Slice<Z, ?, Graph<Theta, Phi>> slice(
      Move<? super Z, ? extends Theta> kf,
      Move<? super Z, ? extends Phi> vf) { return null; }
}
