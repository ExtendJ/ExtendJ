// See issue 199.
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
