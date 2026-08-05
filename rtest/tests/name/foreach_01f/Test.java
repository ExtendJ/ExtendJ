// The enhanced-for parameter can not shadow a local variable name.
// See issue 303.
// .result: COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    String zoinks;
    for (String zoinks : args)
      System.out.println(zoinks);
  }
}
