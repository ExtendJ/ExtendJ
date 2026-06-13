// https://bitbucket.org/extendj/extendj/issues/258/failure-to-find-most-specific-method-when
public class Test {
  public static void main(String[] args) {
    reportError("nothing marks the spot", new Throwable());
    reportError("%s marks the spot", new Throwable(), "x");
    reportError("%s marks the spot", new Test());
    reportError("%s marks the %s", "x", new Throwable());
  }

  static void reportError(String message, Throwable excp, Object... args) {
    System.out.format("Exception (%s): %s%n",
        excp.getClass().getName(),
        String.format(message, args));
  }

  static void reportError(String message, Object... args) {
    System.out.println("Error: " + String.format(message, args));
  }

  public String toString() {
    return "TEST";
  }
}
