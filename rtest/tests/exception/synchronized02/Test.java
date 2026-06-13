// Test a regression in ExtendJ causing inherited attribute evaluation to fail due
// to rewrite caching changes with circular rewrites. The code below triggered an
// exception while evaluating the inherited attribute ReturnStmt.leavesMonitor(Monitor).
// .result=COMPILE_PASS
public class Test {
  void x(Test e) {
    try {
      throw new Error();
    } finally {
      synchronized (this) {
        for (; e.y(); ) {
          return;
        }
      }
    }
  }

  boolean y() {
    return false;
  }
}
