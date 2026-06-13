// Test that enhanced-for can iterate over a an iterable type parameterized
// with a wildcard extends type.
// .result: COMPILE_PASS
public class Test {
  public void printInts(Iterable<? extends Integer> ints) {
    for (Integer i : ints) {
      System.out.println(i);
    }
  }
}
