// Illegal autoboxing conversion.
// See issue 225.
// .result: COMPILE_FAIL

class Test {
  public static void main(String[] args) {
    int i = f(3);
  }

  static int f(Object i) {
    return (int) i; // Can't convert Object->int.
  }
}
