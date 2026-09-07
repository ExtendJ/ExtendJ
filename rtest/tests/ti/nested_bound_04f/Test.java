// Test a nested invocation whose result must be a subtype of a captured type variable with a lower bound.
// .result: COMPILE_FAIL
public abstract class Test {
  abstract <B> Unary<B> inre(B b);
  abstract <Y> Y yttre(Unarily<Y> l, Y y);

  Object tost(Unarily<? super Unary<Nullary>> kan) {
    // Y is equal to the captured type argument of kan, which has the lower bound
    // Unary<Nullary>, so the constraint reduces to Unary<B> <: Unary<Nullary>.
    return yttre(kan, inre(new Nullarily()));
  }
}

class Nullary { }
class Nullarily { }
interface Unary<U> { }
interface Unarily<U> { }
