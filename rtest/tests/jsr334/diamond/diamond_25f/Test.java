// Test ambiguous constructor call error in diamond expression.
// .result=COMPILE_FAIL
public class Test {
  Center<String> a = new Center<>(0, 0);
}

class Center<T> {
  <U extends Number> Center(U a, int b) { }
  <U extends Number> Center(int a, U b) { }
}

