// Test a nested lambda whose body is a generic invocation.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <G, F extends G> Big<G> big(F f);

  BigIfTrue<Big<Big<IfTrue>>> bigIfTrue;

  {
    // The invocation inside the lambda is inferred with target type Big<IfTrue>.
    bigIfTrue.ifTrue(() -> big(new True()));
  }
}

interface Big<T> {
  T bigT();
}
interface IfTrue { }
class True implements IfTrue { }
interface BigIfTrue<E> {
  boolean ifTrue(E e);
}
