// An outer class may extend an inner class from a non-superclass.
// .result=COMPILE_PASS
public class Test {
  public class Inner {
  }
}

class A extends Test.Inner {
  public A() {
    // Provide an enclosing instance for the superclass:
    new Test().super();
  }
}
