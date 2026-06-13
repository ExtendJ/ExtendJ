// Test a simple package anntation (pkg/package-info.java).
// .result=COMPILE_PASS
import pkg.A;

public class Test {
  public static void main(String[] args) {
    new A();
  }
}
