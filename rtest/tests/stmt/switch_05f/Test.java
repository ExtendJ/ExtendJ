// Switch labels must be unique.
// .result: COMPILE_FAIL
public class Test {
  void fail(int in) {
    switch (in) {
      case 18:
      case 0x12: // Error: same as 18.
    }
  }
}
