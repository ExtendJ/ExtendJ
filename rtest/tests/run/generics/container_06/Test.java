// Test using an enhanced-for loop to iterate elements of an
// array stored in a polymorphic container type.
// See issue 278.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    System.out.println(join(
        new Container<String[]>(
            new String[] { "x", " marks", " the spot" })));
  }

  static String join(Container<String[]> values) {
    StringBuilder message = new StringBuilder();
    for (String part : values.get()) {
      message.append(part);
    }
    return message.toString();
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
