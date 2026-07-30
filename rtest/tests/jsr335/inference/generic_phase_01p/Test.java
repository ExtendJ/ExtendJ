// A fixed-arity generic method that is only applicable by loose invocation must
// not compete with a non-generic method applicable in the strict phase.
// .result=EXEC_PASS
public class Test {
  static void m(long x) {
    System.out.println("long");
  }

  static <T> void m(T x) {
    System.out.println("generic");
  }

  public static void main(String[] args) {
    m(1);
  }
}
