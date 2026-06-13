// The enhanced-for parameter can not shadow a local variable name.
// https://bitbucket.org/extendj/extendj/issues/303/variable-shadowing-is-incorrectly-allowed
// .result: COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    String zoinks;
    for (String zoinks : args)
      System.out.println(zoinks);
  }
}
