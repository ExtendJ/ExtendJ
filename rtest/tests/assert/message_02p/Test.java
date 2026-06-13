// The assert message can be a primitive type.
// .result=EXEC_PASS
// .javaOptions: -enableassertions
public class Test {
  public static void main(String[] args) {
    try {
      assert args.length > 100 : 6.3f;
      throw new Error("Expected AssertionError.");
    } catch (AssertionError e) {
      assert e.getMessage().equals("6.3");
    }
  }
}
