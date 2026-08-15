// Test constraint ordering in inference.
// .result: COMPILE_FAIL
import java.io.IOException;

public class Test {
  {
    // The referenced method baah(String) throws IOException which becomes a bound of
    // E2, so the uncaught exception is reported at the invocation.
    waffles("cVvI8GdTfh4", this::baah);
  }

  <T2, R2, E2 extends Exception> R2 waffles(T2 arg, LambDuh<T2, R2, E2> f) throws E2 {
    return f.baah(arg);
  }

  // Overloaded so that Test::baah is an inexact method reference.
  Integer baah(String s) throws IOException {
    return 0;
  }

  Integer baah(StringBuilder sb) throws IOException {
    return 0;
  }
}

interface LambDuh<Ai, Ao, Ae extends Exception> {
  Ao baah(Ai a) throws Ae;
}
