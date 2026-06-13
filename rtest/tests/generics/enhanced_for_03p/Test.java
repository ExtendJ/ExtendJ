// Test that enhanced-for can iterate over a an iterable type parameterized
// with a wildcard super type.
// .result: COMPILE_PASS
public class Test {
  public void print(Iterable<? super Integer> ints) {
    for (Object o : ints) {
      System.out.println(o);
    }
  }
}
