// Test overriding a method with type parameters in a raw anonymous type.
// .result=COMPILE_PASS
import java.util.Collection;
import java.util.Collections;

public class Test {
  interface I<T> {
    Collection<T> x();
  }

  static I builder = new I() {
    public Collection<Integer> x() {
      return Collections.singleton(1333);
    }
  };
}
