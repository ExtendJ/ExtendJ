// .result=COMPILE_FAIL

class Test {
  void m() {
    int a;
    for (Object o : iterable()) {
      a = 3;
      int c = a;
    }
    int b = a;
  }

  Iterable<Object> iterable() {
    return null;
  }
}
