// Diamond is not allowed in anonymous class instance expressions.
// .result=COMPILE_FAIL
class Test {

  class T2_A<T> { }

  {
    T2_A<Integer> bar = new T2_A<>() { };
  }
}
