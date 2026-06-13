// An outer class may extend an inner class from a non-superclass.
// .result=COMPILE_PASS
public class Test {
  public class Inner {
  }

}

class Outer extends Test {
  class A extends Test.Inner {
    public A() {
      // A is an inner class of a subclass of the enclosing class of Test.Inner
      // so we don't need to provide an enclosing instance.
    }
  }
}
