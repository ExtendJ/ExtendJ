// Simple generic container runtime test.
public class Test {
  public static void main(String[] args) {
    Container<Integer> con = new Container<Integer>(new Integer[30]);
    for (int i = 0; i < 30; ++i) {
      con.val[i] = (i + 1) * 3;
    }
    int sum = 0;
    for (int i = 0; i < 30; ++i) {
      int value = con.val[i].intValue();
      sum += value;
      if (value != (i + 1) * 3) {
        System.out.println("wrong value");
        return;
      }
    }
    if (sum != 3 * ((30 * (30 + 1)) / 2)) {
      System.out.println("wrong sum: " + sum);
      return;
    }
  }
}

class Container<T> {
  public final T[] val;

  public Container(T[] val) {
    this.val = val;
  }
}
