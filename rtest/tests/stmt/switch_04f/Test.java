// Switch labels must be unique.
// .result: COMPILE_FAIL
public class Test {
  final int X = 123;
  final int Y = 230;
  final int Z = 01;
  final int U = 02;
  final int V = 1;
  final int W = 3;
  void fail(int in) {
    switch (in) {
      case X:
      case Y:
      case Z:
      case U:
      case V: // Error: V has the same value as Z.
      case W:
      default:
    }
  }
}
