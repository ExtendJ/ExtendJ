// Test checked exception inference for an inexact method reference.
// The read method is overloaded, so Test::read has no exact compile-time
// declaration and the invoked method is found via the target function type.
// .result: COMPILE_PASS
public class Test {
  void m() throws java.io.IOException {
    exec(Test::read);
  }

  static void read() throws java.io.IOException { }

  static void read(int n) throws Exception { }

  static <E extends Exception> void exec(Task<E> t) throws E {
    t.run();
  }
}

interface Task<E extends Exception> {
  void run() throws E;
}
