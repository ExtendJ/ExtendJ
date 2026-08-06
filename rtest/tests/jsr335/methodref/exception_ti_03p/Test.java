// Test checked exception inference for an instance method reference.
// .result: COMPILE_PASS
public class Test {
  Reader r = new Reader();

  void m() throws java.io.IOException {
    String s = exec(r::read);
  }

  static <E extends Exception> String exec(Fun<E> f) throws E {
    return f.get();
  }
}

class Reader {
  String read() throws java.io.IOException { return "FmOtrCRrlDA"; }
}

interface Fun<E extends Exception> {
  String get() throws E;
}
