// The compiler warns at the declaration of a variable arity method with
// no-reifiable variable arity parameter type that is not declared with
// @SafeVarargs.
// .result: COMPILE_WARN
class Test {

  static <T> void boo(T... a) {}

}
