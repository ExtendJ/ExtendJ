// Test executing a default method on an anonymous class instance.
public class Test {
  public static void main(String[] args) {
    new I() { }.run();
  }
}

interface I {
  default void run() {
    System.out.println("x marks the spot");
  }
}
