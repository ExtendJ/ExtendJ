// Test generic bounds checking.
// This test checks that an array type is not within the bounds
// of the type variable T, where T extends java.lang.Object & I.
// .result=COMPILE_FAIL
public class Test<T extends java.lang.Object & I> {
  void test() {
    new Test<Class[]>(); // Error: Class[] does not implement I.
  }
}

interface I {
}
