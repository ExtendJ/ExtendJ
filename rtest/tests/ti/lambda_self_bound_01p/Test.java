// Nested lambda of same type passing a type down and then up the call chain.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <I, O> O lift(Zap<I, O> z, I i);
   { lift(u -> lift(v -> v, u), "wIrnrnkajPM").length(); }
}
interface Zap<L, R> {
  R zap(L t);
}
