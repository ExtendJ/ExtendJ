// An anonymous class can be created in the same enclosing class as its
// superclass.
// .result=COMPILE_PASS
public class Test {
  public class Inner {
    boolean g(int x) {
      return x > 3;
    }
  }

  Inner build() {
    // This anonymous class is an inner class of the enclosing class of
    // Test.Inner so we don't need to provide an enclosing instance.
    return new Inner() {
      boolean g(int x) {
        return x < 8;
      }
    };
  }
}
