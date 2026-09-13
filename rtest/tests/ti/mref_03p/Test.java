// Test a constructor reference to a generic class in a nested generic invocation context.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <N_ extends Crate<?>> N_ into(Sus<N_> factory);

  Crate<String> names = into(Basket::new);
}

interface Sus<H> {
  H sus();
}

interface Crate<B> { }

class Basket<C> implements Crate<C> { }
