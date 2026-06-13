// Test that a final field can be assigned in two separate constructors as long
// as they do not call each other.
// .result=COMPILE_FAIL
class Test {
  final int x;

  Test() {
    this(4);
    x = 5; // Error: multiple assignment to x.
  }

  Test(int i) {
    x = i;
  }
}
