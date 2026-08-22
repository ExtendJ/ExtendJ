// Test inference of a method using an invocation to the same method as argument.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <S> Truck<? extends S> lorry(S seed);

  // The inference of the inner invocation must use separate ivars from the outer invocation.
  Truck<?> r = lorry(lorry(""));
}

interface Truck<T> { }
