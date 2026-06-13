// Sequences of underscores must be between digits.
// .result=COMPILE_FAIL
public class Test {
  void fail() {
    long foo = 0_L;
  }
}
