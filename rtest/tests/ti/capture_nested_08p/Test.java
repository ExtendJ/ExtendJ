// Test inference of a nested invocation result with a wildcard type argument.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <V> Eider<V, ?, V> inner(V na);
  abstract <M, A> M outer(Eider<? super Somateria, A, M> s);
  // V >: String, V >: Somateria
  // M = V resolves to lub(String, Somateria)
  // ? resolves to a capture variable
  Object fischeri = outer(inner("C5vqf5Jieiw"));
}
interface Eider<S, T, B> { }
interface Somateria { }
