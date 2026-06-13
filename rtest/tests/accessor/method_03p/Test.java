// A generic method can be called using an accessor.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    new Test().<Integer>get();
  }

  <A> A get() {
    return null;
  }
}

