// Enum constants can be used in a static context.
// .result=COMPILE_PASS
enum Test {
  O1, O2, O3;

  static {
    System.err.println(O1);
  }
}
