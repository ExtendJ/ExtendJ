// Test type inference with interdependent capture variable bounds.
// .result=COMPILE_PASS
public class Test {
  <He, Li extends He> void g(Jerry<He, Li> c) { }

  void test(Jerry<?, ?> c) {
    // The upper bound of the capture of the second wildcard is the capture of
    // the first wildcard here.
    g(c);
  }
}

class Jerry<H, B extends H> { }
