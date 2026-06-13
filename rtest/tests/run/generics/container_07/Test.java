// Test string concatenation with generic container value.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    String message = cat("x", new Container<String>(" marks the spot"));
    if (!message.equals("x marks the spot")) {
      System.out.println(message);
    }
  }

  static String cat(String prefix, Container<String> suffix) {
    return prefix + suffix.get();
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
