// Type argument inference does not work through method conversion.
// .result=COMPILE_FAIL
class Test {

  class T8_A<T> { }

  void l(T8_A<Integer> t) { }

  {
    l(new T8_A<>());
  }
}
