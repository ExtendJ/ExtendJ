// Test checked exception inference for an exact method reference.
// The thrown exception type E is inferred from the referenced method.
// .result: COMPILE_PASS
public class Test {
  void m() throws java.io.IOException {
    String s = exec(Test::read);
  }

  static String read() throws java.io.IOException { return "URtqADoz9uA"; }

  static <E extends Exception> String exec(Fun<E> f) throws E {
    return f.get();
  }
}

interface Fun<E extends Exception> {
  String get() throws E;
}
