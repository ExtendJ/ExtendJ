// A class extending an inner class from a non-supertype must provide
// an explicit enclosing instance in the super constructor call.
// .result=COMPILE_FAIL
public class Test {
  public class Inner {
  }
}

class A extends Test.Inner {
  public A() {
    // Missing enclosing instance.
  }
}
