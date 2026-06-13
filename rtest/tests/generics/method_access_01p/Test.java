// Test that the correct method call is generated with a type parameter qualifier expression.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    int comp = new Container<Integer>(123).compare(321);
    if (comp != -1) {
      throw new Error();
    }
  }
}

class Container<T extends Number & Comparable> {
  T value;

  Container(T s) {
    value = s;
  }

  @SuppressWarnings("unchecked")
  public int compare(T x) {
    return value.compareTo(x);
  }
}
