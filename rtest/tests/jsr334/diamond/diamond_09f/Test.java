// Diamond may not be used with explicit constructor type arguments.
// .result=COMPILE_FAIL
class Test {

  class T5_1_A<T> {
    <U> T5_1_A() { }
  }

  {
    T5_1_A<String> obj = new <Integer> T5_1_A<>();
  }
}
