// Test error in ExtendJ codegeneration for
// constant folding with enum constants.
// See issue 191.
// .result=EXEC_PASS
// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

public class Test {
  public static enum CatType {
    HOUSE, STRAY,
    LASAGNA, TOM,
    HAT, CHESHIRE
  }

  public static final String cat1 = "Cat in the " + CatType.HAT;
  public static final String cat2 = CatType.TOM + " cat";

  public static void main(String[] args) {
    testEqual("Cat in the HAT", cat1);
    testEqual("TOM cat", cat2);
  }
}
