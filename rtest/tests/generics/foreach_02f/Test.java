// https://bitbucket.org/extendj/extendj/issues/199/missing-type-error-in-enhanced-for-when
// .result=COMPILE_FAIL
import java.util.List;

public class Test {
  public <T extends List<Integer>> String join(T list) {
    String str = "";
    for (String a : list) {
      str += a;
    }
    return str;
  }
}
