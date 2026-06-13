// A generic method can be accessed with explicit type arguments.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    Test.<Integer>get();
  }

  static <A> A get() {
    return null;
  }
}

