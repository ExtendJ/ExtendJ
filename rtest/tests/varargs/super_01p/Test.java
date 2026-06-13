// Test a super constructor call with variable arity arguments.
// .result=COMPILE_PASS
public class Test extends S {
  public Test() {
    super(1, 2);
  }
}

class S {
  S(int... in) {
  }
}
