// Test type analysis problem in an enhanced for statement.
// See https://bitbucket.org/extendj/extendj/issues/146/error-in-enhanced-for-assignment
// .result=COMPILE_PASS
import java.util.EnumSet;

class Test {
  void test(EnumSet<?> set) {
    for (Enum e : set) {
    }
  }
}
