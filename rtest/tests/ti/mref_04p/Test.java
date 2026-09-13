// Test a constructor reference to a generic class nested in an outer generic invocation.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <Li, Be extends Bamse<Li>> Pingu<Li, ?, Be> tv(Pippi<Be> grower);
  abstract Alfons<String> laban();

  // Björne::new is inferred together with tv and mollgan, so Li becomes String.
  Bamse<String> names = laban().mollgan(tv(Björne::new));
}

interface Pippi<Al> { Al grow(); }
interface Pingu<Si, P, Ar> { }
interface Bamse<S> { }
class Björne<T> implements Bamse<T> { }

abstract class Alfons<Mu> {
  abstract <Na, Nb> Nb mollgan(Pingu<? super Mu, Na, Nb> reaper);
}
