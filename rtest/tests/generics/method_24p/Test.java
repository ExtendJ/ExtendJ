// Test a simple generic identity method.
public class Test {
  public static void main(String[] args) {
    String v1 = identity("x marks the spot");
    Number v2 = identity(Integer.valueOf(123));
    Integer v3 = identity(Integer.valueOf(555));
    if (v1.equals("x marks" + " the spot")) {
      System.out.println("pass1");
    }
    if (v2.equals(Integer.valueOf(123))) {
      System.out.println("pass2");
    }
    if (v3.equals(Integer.valueOf(555))) {
      System.out.println("pass3");
    }
  }

  static <T> T identity(T value) {
    return value;
  }
}
