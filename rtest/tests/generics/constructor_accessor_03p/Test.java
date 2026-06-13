// Thest that a single constructor accessor method is generated for multiple
// parameterizations. This is a variant of constructor_accessor_02p where
// the inner class is not static.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    new Test().test();
  }

  void test() {
    C<Integer> i = new C<Integer>();
    C<Float> f = new C<Float>();
    C<Double> d = new C<Double>();
    C<String> s = new C<String>();
  }

  private class C<T> {
  }
}
