// Thest that a single constructor accessor method is generated for multiple
// parameterizations.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    C<Integer> i = new C<Integer>();
    C<Float> f = new C<Float>();
    C<Double> d = new C<Double>();
    C<String> s = new C<String>();
  }

  private static class C<T> {
  }
}
