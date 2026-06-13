// A class extending an inner class from a non-supertype must provide
// an explicit enclosing instance in the super constructor call.
//
// This test differs from extends_inner_02f in that class A is an inner class.
// .result=COMPILE_FAIL
public class Test {
  public class Inner {
  }
}

class Outer {
  class A extends Test.Inner {
    public A() {
      // Missing enclosing instance.
    }
  }
}
