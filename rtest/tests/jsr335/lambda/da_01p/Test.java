// Lambda with return statement should not interfere with
// definite assignment analysis.
// See issue 292.
// .result: COMPILE_PASS
public class Test {
  private final Runnable nothing;

  public Test() {
    nothing = () -> { return; };
  }
}
