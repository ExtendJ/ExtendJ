// A local class declaration can not be used before it is declared.
// .result=COMPILE_FAIL
interface Bort { }

public class Test {
  public static void main(String[] args) {
    Bort bort = new Local(); // Error: Out-of-order use of local class.

    class Local implements Bort {
    }
  }
}
