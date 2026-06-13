// Test a super constructor call with variable arity arguments.
// .result=EXEC_PASS
public class Test extends S {
  public Test() {
    super(1, 2);
  }

  public static void main(String[] args) {
    Test test = new Test();
    if (test.data.length != 2 || test.data[0] != 1 || test.data[1] != 2) {
      throw new Error();
    }
  }
}

class S {
  int[] data;

  S(int i1) {
    data = new int[] { i1 };
  }

  S(int... in) {
    data = new int[in.length];
    for (int i = 0; i < in.length; ++i) {
      data[i] = in[i];
    }
  }
}
