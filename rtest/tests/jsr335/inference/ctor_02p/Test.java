// Variant of ctor_01p using alternate constructor invocation (this()).
// .result: EXEC_PASS
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Test {
  Test(List<String> list) {
  }

  Test(Set<String> set) {
    throw new RuntimeException();
  }

  Test() {
    this(Collections.emptyList());
  }

  public static void main(String[] args) {
    new Test();
  }
}
