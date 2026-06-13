// Test that package access is not altered during type substitution.
// .result=COMPILE_PASS
public class Test {
  void test(I<Integer> i) {
    x("moshi moshi", this);
    i.y();
    i.z();
  }

  static <T> void x(java.lang.String s, T t) {
  }
}

interface I<T> {
  java.util.LinkedList<T> y();

  java.lang.Object z();
}
