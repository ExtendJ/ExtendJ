// Test illegal assignment destination.
// .result=COMPILE_FAIL
public class Test {
  int f() {
    int x = 2;
    (x + 0) -= 2;
    return x;
  }
}
