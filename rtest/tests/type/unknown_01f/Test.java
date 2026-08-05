// An unknown type may not be used as a local variable type.
// See issue 196.
// .result=COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    org.extendj.Thing thing;  // Unknown type.
  }
}
