// Test that throws bound is created according by JLS SE8 §18.5.1.
// .result: COMPILE_FAIL
import java.io.IOException;

public class Test {
  void m() {
    seti(); // Error: IOException not caught or declared thrown.
  }

  <Seti extends IOException> void seti() throws Seti { }
}
