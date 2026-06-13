// Test that assertion code generation works.
// .result=EXEC_PASS
// .javaOptions: -enableassertions
public class Test {
  public static void main(String[] args) {
    // Test an assert condition that is not compile-time constant.
    assert args.length < 100;
  }
}
