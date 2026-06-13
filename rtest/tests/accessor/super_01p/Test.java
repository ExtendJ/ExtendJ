// Test a super method call that needs a super accessor.
// .result=EXEC_PASS
public class Test extends Super {
  public static void main(String[] args) {
    Inner inner = new Test().new Inner();
    if (inner.val() != 324) {
      throw new Error();
    }
  }

  int val() {
    return 243;
  }

  class Inner {
    int val() {
      return Test.super.val(); // Needs accessor.
    }
  }
}

class Super {
  int val() {
    return 324;
  }
}
