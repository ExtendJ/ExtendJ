// Variable being declared as a var cannot be referenced in the initializer.
// .result=COMPILE_FAIL
public class Test {
  public void test(){
    var a = new A(new B(a)); // err
    var b = new A(new B(new A())); // ok
    var c = new A(2*f(c=2)).j; // err
  }

  public int f(int i) {
    return i;
  }

  class A{
    int j = 1;
    public A(B b) { }
    public A() { }
    public A(int i) { }
  }

  class B {
    public B(A a){ }
  }
}
