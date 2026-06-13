// Rethrowing type Throwable must not be reported as thrown (changed in Java 7).
// .result: COMPILE_FAIL
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
