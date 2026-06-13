// Test type analysis problem in an enhanced for statement.
// .result=EXEC_PASS
import java.util.EnumSet;

public class Test {
  public static void main(String[] args) {
    printSet(EnumSet.allOf(E.class));
  }

  static void printSet(EnumSet<?> set) {
    for (Enum e : set) {
      System.out.println(e);
    }
  }
}

class S<E extends Test> {
}

enum E {
  A, B, C;
}
