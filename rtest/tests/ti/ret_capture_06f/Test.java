// Two wildcard-parameterized invocations in one call get distinct capture
// bounds with distinct fresh variables.
// .result: COMPILE_FAIL
public abstract class Test {
  abstract <G> Some<?> house(G x);
  abstract <H> H choose(H a, H b);

  void test() {
    // The two house() calls have distinct parameterizations of Some with fresh variables.
    Some<Thing> something = choose(house(910123), house(58234));
  }
}
interface Some<T> { }
interface Thing { }
