// A local class declaration may not be shadowed in an inner scope.
// .result=COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    class Shadowed {}
    if (args.length == 1) {
      class Shadowed {}  // Illegal.
    }
  }
}
