// Test a lambda throws check against self-referential lub type.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <F extends Exception> void slott(F a, F b, Vitality<F> g);

  {
    // The thrown exception is checked against F = lub(Aj, Oj) which is
    // an intersection mentioning itself.
    slott(new Aj(), new Oj(), () -> { throw new Aj(); });
  }
}

interface Undantag<T> { }
class Aj extends Exception implements Undantag<Aj> { }
class Oj extends Exception implements Undantag<Oj> { }

interface Vitality<E extends Exception> {
  void dock() throws E;
}
