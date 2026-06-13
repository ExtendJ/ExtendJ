// An unknown type may not be used as a local variable type.
// https://bitbucket.org/extendj/extendj/issues/196/no-error-reported-when-using-unknown-type
// .result=COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    org.extendj.Thing thing;  // Unknown type.
  }
}
