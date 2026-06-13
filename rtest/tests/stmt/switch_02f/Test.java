// Switch labels can not be duplicates.
// .result: COMPILE_FAIL
public class Test {
  void fail(int in) {
    switch (in) {
      case 0:
      case 1:
      case 2:
      case 0:
      case 3:
      default:
    }
  }
}
