// Basic type inference using the diamond construct.
// .result=COMPILE_PASS
public class Test {
  void f() {
    java.util.ArrayList<Integer> foo = new java.util.ArrayList<>();
  }
}
