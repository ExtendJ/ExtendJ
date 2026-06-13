// Illegal autoboxing conversion.
// https://bitbucket.org/extendj/extendj/issues/225/illegal-autoboxing-conversion-is
// .result: COMPILE_FAIL
class Test {
  public static void main(String[] args) {
    if (f(100L) != 100L) {
      throw new Error();
    }
  }
  static long f(Object o) {
    return (long) o; // Can't convert Object->long.
  }
}
