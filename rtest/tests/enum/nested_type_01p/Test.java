// Enum declarations can have nested types.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    if (E.C1.c.n != 1234) {
    	throw new Error();
    }
  }
}

enum E {
  C1, C2, C3;

  C c = new C();

  class C {
    int n = 1234;
  }
}
