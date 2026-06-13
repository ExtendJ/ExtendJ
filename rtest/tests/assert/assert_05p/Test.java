// If the -enableassertions flag is not given to Java then assertions are
// not checked during runtime.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    assert args.length > 100; // Does not throw if assertions disabled.
  }
}
