// Assert statements can have side effects in the condition.
// .result=EXEC_PASS
// .javaOptions: -enableassertions
public class Test {
  public static void main(String[] args) {
    int size = 3;
    assert (size = size + 1 + args.length) == 4;
    if (size == 3) {
      throw new Error("Expected assert side effect. Assertions disabled?");
    }
  }
}
