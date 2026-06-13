// This test just validates that the AssertionsDisabled runtime class has
// a field named $assertionsDisabled which is synthetic.
// Otherwise, the test classes/synthetic/_01f would be pointless.
// .classpath=@RUNTIME_CLASSES@
import java.lang.reflect.*;

import runtime.AssertionsDisabled;

public class Test {
  public static void main(String[] args) throws Throwable {
    for (Field f : AssertionsDisabled.class.getDeclaredFields()) {
      System.out.format("%s: synthetic = %s%n", f.getName(), f.isSynthetic());
    }
  }
}
