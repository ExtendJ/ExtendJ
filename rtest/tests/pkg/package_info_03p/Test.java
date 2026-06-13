// Test a simple package anntation (pkg/package-info.java).
// https://bitbucket.org/extendj/extendj/issues/316/parser-fails-on-package-annotation-with
// .result=COMPILE_PASS
import pkg.A;

public class Test {
  public static void main(String[] args) {
    new A();
  }
}
