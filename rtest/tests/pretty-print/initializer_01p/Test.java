// .result=COMPILE_OUT
// .options=XprettyPrint
public class Test {
  static {
    // Empty static initializer should not be printed.
  }

  int x;

  {
    x = 10;
  }
}
