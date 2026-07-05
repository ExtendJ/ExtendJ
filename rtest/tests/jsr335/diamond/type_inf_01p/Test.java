// .result: COMPILE_PASS
import java.util.*;

public class Test {
  void pass(Sub[] s) {
    Set<Sup> set = new HashSet<>(Arrays.asList(s));
  }
}

class Sup { }
class Sub extends Sup { }
