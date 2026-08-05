// A non-void method must not return a value if it does not terminate.
// This test exposes a bug in the normal method completion analysis in ExtendJ.
// See issue 184.
// .result=COMPILE_PASS
public class Test {
  public String loop1() {
    for (;;) { } // OK
  }

  public String loop2(String[] args) {
    for (;;) {
      for (String p : args) {
        if (p.equals("foo")) {
          break; // OK
        }
      }
    }
  }
}
