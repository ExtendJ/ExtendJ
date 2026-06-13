// Tests that a classfile is generated for a static import.
// .result=EXEC_PASS
// .classpath=@TEST_DIR@
// .compile_order=Test.java
import static p1.A.*;
import static p2.B.m2;

public class Test {
  public static void main(String[] args) {
    m1();
    m2();
  }
}
