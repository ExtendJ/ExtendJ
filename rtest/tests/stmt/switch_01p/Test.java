// Test code generation for large switch case label value span.
// .result=EXEC_PASS
class Test {
  public static void main(String[] args) {
    if (f(1089111991) != 0) {
      throw new Error();
    }
    if (f(975563525) != 1) {
      throw new Error();
    }
    if (f(1751289691) != 2) {
      throw new Error();
    }
  }
  static int f(int i) {
    switch (i) {
      case 1089111991:
        return 0;
      case 975563525:
        return 1;
      case 1751289691:
        return 2;
    }
    return -1;
  }
}
