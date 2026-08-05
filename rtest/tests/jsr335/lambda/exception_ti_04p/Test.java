// Nested lambda expression with throws. Ensure the nested throw type is not
// reached in the outher lambda body.
// .result: COMPILE_PASS
public class Test {
  static <T> T exec(Task t, T x) { t.run(); return x; }
  static String scuttle() throws java.io.IOException { return "fgRL2_nolI4"; }

  String r = exec(() -> {
    Callable<String> c = () -> scuttle();   // legal: Callable declares throws Exception
  }, "");
}

interface Task { void run(); }
interface Callable<V> { V call() throws Exception; }
