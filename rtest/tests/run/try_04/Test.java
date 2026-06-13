// Test that exception handlers are kept intact when dead bytecode is removed.
public class Test {
  public static void main(String[] args) {
    int x;
    for (int i = 1; true; ++i) { // Dead update statement.
      x = i;
      break;
    }
    try {
      f(x);
    } catch (Error t) {
      System.out.println(t.getMessage());
    } catch (Exception t) {
      System.out.println("fail");
    }
  }

  static void f(int i) throws Exception {
    throw new Error("" + i + " marks the spot");
  }
}
