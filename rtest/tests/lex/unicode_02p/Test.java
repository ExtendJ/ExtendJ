// Test varying number of leading backslashes before unicode escape.
public class Test {
  public static void main(String[] args) {
    System.out.println("\u0021");
    System.out.println("\\u0021");
    System.out.println("\\\u0021");
    System.out.println("\\\\u0021");
    System.out.println("\\\\\u0021");
    System.out.println("\\\\\\u0021");
  }
}
