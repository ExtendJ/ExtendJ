// Test recursive type substitution.
// .result=COMPILE_PASS
import java.util.*;
public class Test {
  static class Ref { }
  static class Obj { }
  TreeMap<Ref, ArrayList<Obj>> map = new TreeMap<Ref, ArrayList<Obj>>();

  void test() {
    map.clear();
  }
}
