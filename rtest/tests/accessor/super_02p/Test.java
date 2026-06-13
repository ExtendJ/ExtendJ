// Test generating a super accessor for a parameterized target method.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    C.Inner foo = new C().new Inner();
    foo.baz();
  }
}

class Super {
  <T> T baz() {
    return null;
  }
}

class C extends Super {
  class Inner {
    void baz() {
      C.super.<Integer>baz();
    }
  }
}
