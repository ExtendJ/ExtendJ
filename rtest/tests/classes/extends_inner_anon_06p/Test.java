// An anonymous class can be created inside a class extending the outer class of
// its superclass.
// .result=COMPILE_PASS
public class Test {
  public class Inner {
    void f() {
    }
  }

}

class Outer extends Test {
  {
    new Inner() {
      void f() {
      }
    };
  }
}
