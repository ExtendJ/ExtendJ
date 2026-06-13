// .result=COMPILE_PASS
import java.util.List;

public class Test {
  public <T extends List<Integer>> int sum(T list) {
    int sum = 0;
    for (Integer i : list) {
      sum += i;
    }
    return sum;
  }
}
