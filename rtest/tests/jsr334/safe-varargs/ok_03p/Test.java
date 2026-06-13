// Variable arity parameter of reifiable type.
// .result: COMPILE_PASS
class Test {

  static <T> void boo(T t, java.util.LinkedList<?>... a) {}

}
