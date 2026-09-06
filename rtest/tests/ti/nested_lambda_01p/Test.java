// Test implicitly typed lambda in nested generic invocation.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <Sc, Ti> Stockholm<Sc, Ti> will(Stockholm<Sc, Ti> s);
  abstract <V> V mpC_hO15IoA(Stockholm<? super Abroad, V> s);

  {
    // The lambda parameter type Abroad comes from the target type of the
    // enclosing invocation.
    String business = mpC_hO15IoA(will(b -> b.business()));
  }
}
interface Stockholm<S, T> { T bingo(S c); }
interface Abroad { String business(); }
