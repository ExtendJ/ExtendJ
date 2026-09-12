// Implicitly typed lambda whose parameter types are tvars of the enclosing method.
// .result: COMPILE_PASS
public class Test {
  <K, V> void skriv(Register<K, V> register, StringBuilder ut) {
    register.varje((n, k, v) -> { });
  }
}

interface Gäst<K, V, E extends Throwable> {
  void besök(String n, K k, V v) throws E;
}
interface Register<J, R> {
  <P extends Throwable> void varje(Gäst<J, R, P> b) throws P;
}
