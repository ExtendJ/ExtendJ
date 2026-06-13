// Unchecked warning expected at the third call to addToList.
// .result: COMPILE_WARN
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
    List<List<Integer>> l3 = new LinkedList<List<Integer>>();
    addToList(l1, new Integer(2), new Integer(3));
    addToList(l2, new Integer(5), new Integer(7));
    addToList(l3, l1, l2);
  }

}
