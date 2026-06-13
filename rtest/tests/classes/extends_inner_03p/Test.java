// An outer class may extend an inner class from a non-superclass.
//
// This test differs from extends_inner_01p in that the subclass of Test.Inner
// is itself an inner class.
// .result=COMPILE_PASS
public class Test {
  public class Inner {
  }
}

class Outer {
  class A extends Test.Inner {
    public A() {
      // Provide an enclosing instance for the superclass:
      new Test().super();
    }
  }
}
