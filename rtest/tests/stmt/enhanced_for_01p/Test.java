// Test type analysis problem in an enhanced for statement.
// See issue 146.
// .result=COMPILE_PASS
import java.util.EnumSet;

class Test {
  void test(EnumSet<?> set) {
    for (Enum e : set) {
    }
  }
}
