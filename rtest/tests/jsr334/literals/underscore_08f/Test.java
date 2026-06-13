// Sequences of underscores must be between digits.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    double bar = 1._2;
  }
}
