// It is an error if @SafeVarargs is used on a class declaration.
// .result: COMPILE_FAIL
class Test {

  @SafeVarargs class C {}

}
