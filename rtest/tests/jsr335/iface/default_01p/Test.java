// Test executing a default method.
public class Test implements I {
  public static void main(String[] args) {
    new Test().run();
  }
}

interface I {
  default void run() {
    System.out.println("x marks the spot");
  }
}
