// If the superclass constructor with no arguments is private, then a default
// constructor can not be generated.
// .result=COMPILE_FAIL

class Super {
  Super(int x) {
  }

  private Super() {
  }
}

public class Test extends Super {
  // Error: superclass constructor is private.
}
