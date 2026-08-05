// Tests that method type argument inference works for methods in parameterized types.
// See issue 124.
// .result=COMPILE_PASS
class Test {
  class A<T> {
    <U> U m() {
      return null;
    }
  }

  {
    A<String> a = new A<String>();
    Integer i = a.m();
  }
}
