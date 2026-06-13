// The @SafeVarargs annotation may not be used on variable declarations.
// .result: COMPILE_FAIL
class Test {

  {
    @SafeVarargs int i;
  }

}
