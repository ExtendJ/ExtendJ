// Does not require @SafeVarargs.
// .result: COMPILE_PASS
class Test {

  void m(Object... a) { }

  {
    java.util.List<Integer> list = null;
    m(list);
  }

}
