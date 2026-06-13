// Assignment context type inference.
// This test checks that two kinds of type substitution can happen
// simultaneously: first, there is type substitution to substitute the
// type parameter T of the class C in the type of the first argument
// of the test method, then there is type substitution to substitute
// the method type parameter R after type inference infers the type
// R=Integer.
// See https://bitbucket.org/extendj/extendj/issues/153/problem-with-type-inference-from-expected
// .result=COMPILE_PASS
public class Test {
  void test(C<String> c, Impl i) {
    C<Integer> r = c.x(i);
  }
}

interface C<T> {
  <R> C<R> x(I<T, R> function);
}

interface I<O, N> {
}

class Impl implements I<String, Integer> {
}
