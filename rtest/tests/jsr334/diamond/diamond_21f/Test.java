// Type inference on array creation.
// .result=COMPILE_FAIL
public class Test {
  void f() {
    java.util.List<Integer>[] a = new java.util.ArrayList<>[12];
  }
}
