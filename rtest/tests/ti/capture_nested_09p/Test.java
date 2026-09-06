// Test an exact method reference in a nested invocation with a wildcard result.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <A> Tadorna<A, ?> inner(Shelduck<? super Gravand, A> m);
  abstract <C, G> G outer(Tadorna<G, C> t);
  // The method reference bounds A <: String, A = G is lifted
  // to outer inference with the bound.
  String albellus = outer(inner(Gravand::qz2SeEzxMuE));
}
interface Gravand { String qz2SeEzxMuE(); }
interface Tadorna<T, S> { }
interface Shelduck<B, K> { K kvak(B b); }
