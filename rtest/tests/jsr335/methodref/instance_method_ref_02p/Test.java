// Regression test for a bug where we incorrectly skipped generic instance method candidates
// for a method reference.
public class Test {
  public static void main(String[] args) {
    Test t = new Test();
    System.out.println(call(t::<String>id));
  }

  static String call(F f) { return f.apply("A8hwn5aJz3k"); }

  <T> T id(T x) { return x; }
}

interface F { String apply(String s); }
