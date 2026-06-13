// Assert statements can have a message included in the thrown AssertionError.
// .result=EXEC_PASS
// .javaOptions: -enableassertions
public class Test {
  public static void main(String[] args) {
    try {
      assert args.length > 100 : "6 laxar i en laxask";
      throw new Error("Expected AssertionError.");
    } catch (AssertionError e) {
      assert e.getMessage().equals("6 laxar i en laxask");
    }
  }
}
