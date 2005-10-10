public class TestLogical {
  public static void main(String[] args) {
    boolean t = true;
    boolean f = false;
    boolean q = false;

    boolean u = t && f && q;
    System.out.println(u); // false
    boolean v = t || f || q;
    System.out.println(v); // true
  }
}
