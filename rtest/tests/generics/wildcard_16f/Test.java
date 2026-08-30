// Test invocation conversion targeting captured wildcard.
// .result=COMPILE_FAIL
public class Test {
  void test(Aelita<?> a) {
    a.aelita(new Object()); // Error: nothing except null is assignable to the capture of the wildcard.
  }
}

class Aelita<T> {
  void aelita(T v) { }
}
