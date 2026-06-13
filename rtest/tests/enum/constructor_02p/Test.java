// Test calling constructor within constructor.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    if (E.A0.i != 0xFFFF) {
      throw new Error("A0");
    }
    if (E.A1.i != 123) {
      throw new Error("A1");
    }
    if (E.A2.i != 0xFFFF) {
      throw new Error("A2");
    }
  }
}

enum E {
  A0, A1(123), A2;

  public int i;

  private E() {
    this(0xFFFF);
  }

  E(int i) {
    this.i = i;
  }
}
