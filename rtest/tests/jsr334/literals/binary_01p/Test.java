// Test binary literals.
// .result=COMPILE_PASS
public class Test {
  public static void main(String[] args) {
    long foo;
    foo = 0b0;
    foo = 0b1;
    foo = 0b0101;
    foo = -0b0101;
    foo = +0b0101;
    foo = 0b0L;
    foo = 0b1l;
    foo = 0b0101L;
    foo = -0b0101L;
    foo = +0b0101l;
  }
}
