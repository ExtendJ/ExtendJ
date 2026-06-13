// Test generic bounds checking.
// This test checks that an array type is within the bounds of a wildcard
// type extending java.lang.Object.
// .result=COMPILE_PASS
public class Test<T extends java.lang.Object> {
  void test() {
    new Test<Class[]>();
  }
}
