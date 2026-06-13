// Test a regression in code generation causing a static field to be
// accessed as if it were not static in bytecode.
// This was caused by modifier flushing during code generation.
// .result=EXEC_PASS
public class Test {
  static String remoteStaticField = "hi";

  public static void main(String argv[]) {
    Other o = new Other();
    if (!o.get().equals("hi")) {
      throw new Error("unexpected return value");
    }
  }
}

class Other {
  String get() {
    return Test.remoteStaticField;
  }
}
