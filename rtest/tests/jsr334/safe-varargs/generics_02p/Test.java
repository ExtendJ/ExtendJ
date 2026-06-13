// This test exposed a stack overflow issue in ExtendJ.
// https://bitbucket.org/extendj/extendj/issues/253/crash-when-compiling-cayenne-in-the
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
