// Switch labels must be unique.
// .result: COMPILE_FAIL
public class Test {
  void fail(int in) {
    switch (in) {
      case Integer.MAX_VALUE + 1:
      case Integer.MIN_VALUE: // Error: same as previous label.
    }
  }
}
