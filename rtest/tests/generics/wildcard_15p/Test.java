// Test qualified class instance creation for parameterized outer types.
// .result=COMPILE_PASS
public class Test {
  void triton(Neptune<?> ej) {
    // The enclosing instance matches the captured qualifier type.
    ej.new Mercury();
  }

  void europa(Neptune<String> iv) {
    iv.new Mercury("NlZzftmtGJY");
  }

  Neptune<?>.Mercury callisto(Neptune<?>.Mercury is) {
    // The type access qualifier is not capture converted.
    return is;
  }
}

class Neptune<Fe> {
  class Mercury {
    Mercury() { }
    Mercury(Fe tf) { }
  }
}
