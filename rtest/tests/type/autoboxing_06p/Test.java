// Test autoboxing conversion.
// .result: EXEC_PASS
class Test {
  public static void main(String[] args) {
    if (f(100L) != 100L) {
      throw new Error();
    }
  }
  static long f(Long o) {
    return o;
  }
}
