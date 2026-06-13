// Test type inference for simple generic method access.
// .result=COMPILE_PASS
public class Test {
  <T extends Number> T num() {
    return null;
  }

  void m() {
    Number num = num();
  }
}
