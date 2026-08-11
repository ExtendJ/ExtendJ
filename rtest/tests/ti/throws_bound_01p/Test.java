// A `throws a` bound (JLS SE8 §18.2.5) makes bound set resolution
// instantiate the inference variable a to RuntimeException when the proper
// upper bounds of a are at most Exception, Throwable, or Object.
// .result: COMPILE_PASS
public class Test {
  static <Pam extends Exception> void bird(Bird<Pam> block) throws Pam {
    block.execute();
  }

  void test() {
    // The lambda body throws only unchecked exceptions.
    // The call to bird() must not require handling a checked exception.
    bird(() -> { throw new RuntimeException(); });
  }
}

interface Bird<P extends Exception> {
  void execute() throws P;
}
