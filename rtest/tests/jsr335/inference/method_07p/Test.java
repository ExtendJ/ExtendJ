// This was adapted from a Stack Overflow question:
// https://stackoverflow.com/questions/40154690/java-8-type-inference-bug
// Both method calls should bind to the String argument method.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    method("");
    method(get());
  }

  static void method(String v) {
    System.out.println("String");
  }

  static void method(Object v) {
    System.out.println("Object");
  }

  interface I {
  }

  static <T extends I> T get() {
    return null;
  }
}
