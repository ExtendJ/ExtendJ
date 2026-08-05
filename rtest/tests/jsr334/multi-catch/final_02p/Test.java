// A multi-catch parameter may be explicitly declared final (otherwise, it is implicitly final).
// See issue 298.
// .result: COMPILE_PASS
import java.io.*;

public class Test {
  public static void main(String[] args) {
    // Classic try-statement final multi-catch parameter.
    try {
      thrower();
    } catch (final Ex1 | Ex2 e) {
    }

    // Try-with-resources final multi-catch parameter.
    try (OutputStream out = new FileOutputStream(args[0])) {
      Thread.sleep(100);
    } catch (final IOException | InterruptedException e) {
    }
  }

  static void thrower() throws Ex1, Ex2 { }
}

class Ex1 extends Exception { }
class Ex2 extends Exception { }
