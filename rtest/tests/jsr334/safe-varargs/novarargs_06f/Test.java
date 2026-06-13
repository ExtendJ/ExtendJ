// @SafeVarargs may not be used on enum declarations.
// .result: COMPILE_FAIL
class Test {

  @SafeVarargs enum E { }

}
