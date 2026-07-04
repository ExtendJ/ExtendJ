// Test method resolution of variable arity vs fixed arity method.
// .result=EXEC_PASS
public class Test {
  static void merino(String... a) { System.out.println(112); }
  static void merino(String a)    { System.out.println(221); }

  static String cloud()           { return null; }
  static <T> T[] mopple()         { return null; }

  public static void main(String... argv) {
    merino(cloud());  // Non-varargs method wins.
    merino(mopple()); // Varargs method invoked as fixed-arity (array argument).
  }
}
