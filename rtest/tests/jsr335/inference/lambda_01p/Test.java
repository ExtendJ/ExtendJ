// Test type inference using generic array type.
// The type variable T is inferred from the array argument
// alone, since the implicitly typed lambda is not pertinent to
// applicability, and the lambda parameter type is derived from the
// inferred T type.
// .result: COMPILE_PASS
public class Test {
  int size = 2;

  void run(Test[] input) {
    check(input, t -> t.size == 2);
  }

  <T> void check(T[] input, Predicate<T> p) { }
}

interface Predicate<T> {
  boolean test(T value);
}
