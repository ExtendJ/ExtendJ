// Test generic container with primitive array argument.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    int[] values = new int[100];
    for (int i = 0; i < 100; ++i) {
      values[i] = (i * i * 255 + i * 6) % 100;
    }
    run(values);
  }

  static void run(int[] values) {
    Container<int[]> con = new Container<int[]>(values);
    if (con.get()[43] != 53 || con.get()[44] != 44) {
      System.out.println("wrong values");
    }
  }

}

class Container<T> {
  private final T value;

  public Container(T value) {
    this.value = value;
  }

  T get() {
    return value;
  }
}
