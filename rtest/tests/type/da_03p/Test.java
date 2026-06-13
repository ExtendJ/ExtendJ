// Test that a final field can be assigned in two separate constructors as long
// as they do not call each other.
// .result=COMPILE_PASS
class Test {
  final int x;

  Test() {
    x = 3;
  }

  Test(int i) {
    x = i;
  }
}
