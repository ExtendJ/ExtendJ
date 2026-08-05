// This test exposed a stack overflow issue in ExtendJ.
// See issue 253.
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  public static void main(String[] args) {
    setParams(Collections.singletonMap("a", null));
  }

  @SafeVarargs
  static void setParams(Map<String, ?>... params) {
  }
}
