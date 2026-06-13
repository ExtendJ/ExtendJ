// Variable arity parameter of non-reifiable type.
// .result: COMPILE_WARN
class Test {

  static <T> void boo(T[]... a) {}

}
