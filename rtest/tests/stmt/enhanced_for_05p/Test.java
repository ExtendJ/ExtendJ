// Test that the final modifier is allowed in the enhanced for parameter
// declaration.
// .result=COMPILE_PASS
class Test {
  void test(int[] in) {
    for (final int i : in) {
    }
  }
}
