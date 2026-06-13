// Diamond can only be used in a class instance expression.
// .result=COMPILE_FAIL
public class Test {
  void f() {
    java.util.List<> foo = new java.util.ArrayList<Integer>();
  }
}
