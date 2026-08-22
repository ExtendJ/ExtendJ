// Test inference of a method using an invocation to the same method as argument.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <S> Car<? extends S> car(S seed);

  // The inference of the inner invocation must use separate ivars from the outer invocation.
  Car<?> train = car(car(car(car(""))));
}

interface Car<T> { }
