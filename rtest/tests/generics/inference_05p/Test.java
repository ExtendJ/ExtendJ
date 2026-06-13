// Test generic method type inference using raw types.
// .result=EXEC_PASS
public class Test {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    MyList list = new MyList();
    MyComparator comparator = new MyComparator();
    Util.sort(list, comparator);
  }
}

interface List<T> {
}

class MyList<T> implements List<T> {
}

interface Comparator<T> {
  int compare(T a, T b);
}

class MyComparator implements Comparator {
  public int compare(Object a, Object b) {
    return -1;
  }
}

class Util {
  public static <T> void sort(List<T> list, Comparator<T> comparator) {
  }
}
