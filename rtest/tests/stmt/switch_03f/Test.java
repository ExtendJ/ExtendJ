// Switch labels must be unique.
// .result: COMPILE_FAIL
public class Test {
  void fail(int in) {
    switch (in) {
      case 0:
      case 1:
      case 2:
      default:
      case 3:
      default: // Error: duplicate default label.
    }
  }
}
