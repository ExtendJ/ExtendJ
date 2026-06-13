// Rethrowing type Throwable must not be reported as thrown in Java 7.
// .result: COMPILE_PASS
public class Test {
  public static void main(String[] args) {
    try {
      g();
    } catch (Throwable t) {
      throw t;
    }
  }

  static void g() {
  }

}
