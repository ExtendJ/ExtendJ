// Test super constructor access of a generic constructor in an enclosing instance.
// .result=COMPILE_PASS
class Outer {
  class Inner {
    <T extends String> Inner(T x) {
    }
  }

}

public class Test extends Outer.Inner {
  <T extends String> Test(Outer enclosing, T t) {
    enclosing.<T>super(t);
  }
}
