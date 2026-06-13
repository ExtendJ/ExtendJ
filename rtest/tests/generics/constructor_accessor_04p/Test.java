// Test that a constructor accessor can be generated for a substituted generic type.
// This test caused an exception in a dev version of ExtendJ because type lookup did
// not find the type of the constructor parameter.
// .result=COMPILE_PASS
public class Test<T extends Number> {
  private Test(T x) {
  }

  public Test<Integer> build(int i) {
    return new Test<Integer>(i) { };
  }
}
