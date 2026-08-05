// Test case from BitBucket issue #205.
// See issue 205.
// .result: COMPILE_PASS
public class Test {
  static public class Container {
    public String o = "x";
  }

  void foo(Container con) {
    ((String) ((Test.Container) con).o).length();
  }
}
