// It is an error if diamond is used in a class instance expression to
// instantiate a class that is not generic.
// .result=COMPILE_FAIL
public class Test {
  void f() {
    Integer foo = new Integer<>();
  }
}
