// Tests that method type argument inference works for methods in parameterized types.
// https://bitbucket.org/extendj/extendj/issues/124/type-argument-inference-does-not-work-for
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
