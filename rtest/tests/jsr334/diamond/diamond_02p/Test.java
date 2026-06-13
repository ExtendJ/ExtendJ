// Type inference with subtype constraint.
// .result=COMPILE_PASS
class Test {
  void f() {
    java.util.List<Integer> foo = new java.util.ArrayList<>();
  }
}
