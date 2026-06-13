// Test regression in field lookup that caused incorrect duplicate declaration errors.
// .result=COMPILE_PASS

public class Test {
  int f(I<Integer> in) {
    return in.x.intValue();
  }
}

abstract class I<T extends Number> {
    T x, y;
}
