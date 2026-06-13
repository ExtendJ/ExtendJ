// A default constructor can not be generated for a class
// extending an inner class, with different direct supertypes.
// .result=COMPILE_FAIL
public class Test {
  public class Inner {
  }
}

class A {
  class B extends Test.Inner {
  }
}
