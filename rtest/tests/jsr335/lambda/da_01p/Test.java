// Lambda with return statement should not interfere with
// definite assignment analysis.
// https://bitbucket.org/extendj/extendj/issues/292/lambda-with-return-statement-interferes
// .result: COMPILE_PASS
public class Test {
  private final Runnable nothing;

  public Test() {
    nothing = () -> { return; };
  }
}
