// Test overriding a method with type parameters in a raw anonymous type.
// .result=COMPILE_PASS
import java.util.Collection;
import java.util.Collections;

public class Test {
  static class C<T> {
    Collection<T> y;
  }

  static C builder = new C() {
    @SuppressWarnings("unchecked")
    public void test(Collection<Integer> a) {
      y = a; // Unchecked warning.
    }
  };

  @SuppressWarnings("unchecked")
  void test(C c, Collection<Number> a) {
    c.y = a; // Unchecked warning.
  }
}
