// This was adapted from a Stack Overflow question:
// https://stackoverflow.com/questions/40154690/java-8-type-inference-bug
// It is different from the Stack Overflow question in that
// the upper bound for the type parameter T is a class type.
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

  static class C {
  }

  static <T extends C> T get() {
    return null;
  }
}
