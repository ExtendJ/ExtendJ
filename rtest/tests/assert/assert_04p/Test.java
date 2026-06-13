// Assert statements throw an AssertionError if the condition is false.
// .result=EXEC_PASS
// .javaOptions: -enableassertions
public class Test {
  public static void main(String[] args) {
    try {
      assert args.length > 100;
      throw new Error("Expected assertion error!");
    } catch (AssertionError e) {
    }
  }
}
