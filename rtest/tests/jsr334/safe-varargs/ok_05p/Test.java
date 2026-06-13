// No unchecked warning expected.
// .result: COMPILE_PASS
import java.util.LinkedList;
import java.util.List;

class Test {

  @SuppressWarnings("unchecked")
  static <T> void addToList(List<T> l, T... a) {
    for (T item : a) {
      l.add(item);
    }
  }

  void foo() {
    List<Integer> l1 = new LinkedList<Integer>();
    List<Integer> l2 = new LinkedList<Integer>();
    addToList(l1, Integer.valueOf(2), Integer.valueOf(3));
    addToList(l2, Integer.valueOf(5), Integer.valueOf(7));
  }

}
