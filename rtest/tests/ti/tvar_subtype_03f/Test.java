// Test nested invocation assigned to non-inference type variable.
// .result: COMPILE_FAIL
public abstract class Test<T> {
  abstract <B> Unary<B> inre(B b);
  abstract Object yttre(T t);

  {
    yttre(inre(new Nullary()));
  }
}

class Nullary { }
interface Unary<U> { }
