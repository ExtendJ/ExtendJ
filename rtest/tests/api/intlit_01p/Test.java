// Test that the IntegerLiteral constructor initializes a literal that
// returns the correct constant value.
// https://bitbucket.org/extendj/extendj/issues/218/integerliteral-generates-bad-bytecode-when
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@:@RUNTIME_CLASSES@
import org.extendj.ast.IntegerLiteral;

import static runtime.Test.testTrue;

public class Test {
  public static void main(String[] args) {
    testTrue(new IntegerLiteral("0xF00BAA").constant().intValue() == 0xF00BAA);
    testTrue(new IntegerLiteral("0123").constant().intValue() == 0123);
    testTrue(new IntegerLiteral("1010").constant().intValue() == 1010);
  }
}
