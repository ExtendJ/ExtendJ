// Test type inference for a generic method invocation in the body of an
// anonymous class.
// See issue 347.
// .result: COMPILE_PASS
import java.util.Collection;
import java.util.List;

public class Test {
  Runnable target = new Runnable() {
    public void run() {
      List<String> list = null;
      element(list); // This invocation has no target type.
    }
  };

  static <X> void element(Collection<X> c) {
  }
}
