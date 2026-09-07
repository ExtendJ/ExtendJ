// .result: COMPILE_PASS
public abstract class Test {
  abstract <B> Unary<B> inre(B b);
  abstract <Y> Y yttre(Unarily<Y> l, Y y);

  Object taost(Unarily<? super Unary<Nullary>> kan) {
    // Y is equal to the captured type argument of kan, which has the lower bound
    // Unary<Nullary>, so the constraint reduces to Unary<B> <: Unary<Nullary>.
    return yttre(kan, inre(new Nullary()));
  }
}

class Nullary { }
interface Unary<U> { }
interface Unarily<U> { }
