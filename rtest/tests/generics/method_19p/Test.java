// Tests that type inference works inside a field declaration initializer.
// This is a regression test for a NullPointerException problem.
// See issue 212.
// .result: COMPILE_PASS
import java.util.Collections;
import java.util.List;

public class Test<E> {
  List<E> list = Collections.emptyList();
}
