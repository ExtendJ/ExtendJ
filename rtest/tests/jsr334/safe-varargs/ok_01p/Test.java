// Legal uses of @SafeVarargs.
// .result: COMPILE_PASS
class Test {

  @SafeVarargs
  static <T> void foo(T... a) { }

  @SafeVarargs
  final <T> void bar(T... a) { }

}
