// Test a simple package anntation (pkg/package-info.java).
// See issue 316.
// .result=COMPILE_PASS
import pkg.A;

public class Test {
  public static void main(String[] args) {
    new A();
  }
}
