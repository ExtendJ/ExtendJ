// Diamond is allowed with inferred constructor type arguments.
// .result=COMPILE_PASS
class Test {

  class T5_2_A<T> {
    <U> T5_2_A() { }
  }

  {
    T5_2_A<String> obj = new T5_2_A<>();
  }
}
