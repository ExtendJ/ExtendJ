// If the superclass constructor with no arguments is private, then a default
// constructor can not be generated.
// .result=COMPILE_FAIL

class Super {
  Super(int x) {
  }

  private Super() {
  }

  void foo() {
  }
}

public class Test {
  void m() {
    // Error: superclass constructor is private.
    new Super() {
      @Override void foo() {
      }
    };
  }
}
