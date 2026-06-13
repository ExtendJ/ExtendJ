// Test that enhanced-for can iterate over a an iterable type parameterized
// with a wildcard extends type.
// Autoboxing is used to convert the element type to the loop variable type.
// .result: COMPILE_PASS
public class Test {
  public void printInts(Iterable<? extends Integer> ints) {
    for (int i : ints) {
      System.out.println(i);
    }
  }
}
