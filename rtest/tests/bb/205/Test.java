// Test case from BitBucket issue #205.
// https://bitbucket.org/extendj/extendj/issues/205/type-propagation-fails-in-certain-rewrites
// .result: COMPILE_PASS
public class Test {
  static public class Container {
    public String o = "x";
  }

  void foo(Container con) {
    ((String) ((Test.Container) con).o).length();
  }
}
