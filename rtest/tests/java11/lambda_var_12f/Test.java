// .result=COMPILE_FAIL
interface UpperCase{
  String upperCase(String s);
}
public class Test {
  {
    UpperCase upper = var s -> s.toUpperCase(); // Syntax error (missing parens).
  }
}
