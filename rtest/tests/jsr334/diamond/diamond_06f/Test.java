// Diamond can only be used in a class instance expression.
// .result=COMPILE_FAIL
class Test<T> {
  class Test<> {
  }
}
