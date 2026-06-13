// Test using enum constructor to initialize enum constants.
// .result=COMPILE_PASS
enum E {
  C1(1), C2(2);

  private final int index;

  E(int index) {
    this.index = index;
  }
}

class Test {
  Object o = E.C1;
}
