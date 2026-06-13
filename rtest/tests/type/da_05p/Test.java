// Test that a final field can be assigned in two separate constructors as long
// as they do not call each other.
// This is a variation of type/da_03p, but with an extra inner class.
// .result=COMPILE_PASS
class Test {
  class Inner {
    final int x;

    Inner() {
      x = 3;
    }

    Inner(int i) {
      x = i;
    }
  }
}
