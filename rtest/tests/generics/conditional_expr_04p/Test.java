// Test conditional expression type analysis.
// .result=COMPILE_PASS
public class Test {
  void test(boolean a, C1 b, C2 c) {
    f(a ? b : c);
  }

  <R> void f(S<R> x) {
  }
}

class S<T> {
}

class C1 extends S<Integer> {
}

class C2 extends S<Integer> {
}
