// Multiple resources, trailing semicolon.
// .result=COMPILE_PASS
public class Test {
  public static void main(String[] args) {
    try (java.io.PrintStream resource1 = System.out;
        java.io.PrintStream resource2 = System.out;) {
    }
  }
}
