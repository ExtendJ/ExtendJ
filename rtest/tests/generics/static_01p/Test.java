// Test a static field can be accessed using a raw type qualifier.
// .result=COMPILE_PASS
class Test {
  int m() {
    int m = G.y;
    return (m * m) / 2;
  }
}

class G<T> {
  static int y = 4;
  T x;
}
