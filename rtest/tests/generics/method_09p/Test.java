// Test static call to a non-generic method in a raw type inside a raw type.
// .result=COMPILE_PASS
public class Test {
  public static void main(String[] args) {
    int i = A.B.f();
  }

  static class A<T> {
    static class B<T> {
      static int f() {
        return 112;
      }
    }
  }
}

