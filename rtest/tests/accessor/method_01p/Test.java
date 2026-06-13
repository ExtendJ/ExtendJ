// Test accessor generated for the correct enclosing method.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    if (!new X().y().z().f()) {
      throw new Error("wrong value");
    }
  }
}

class X {
  boolean m(int x) {
    return x == 1999;
  }

  public Y y() {
    return new Y();
  }
}

class Y extends X {
  void m() {
  }

  public Z z() {
    return new Z();
  }

  class Z {
    boolean f() {
      return m(1999);
    }
  }
}
