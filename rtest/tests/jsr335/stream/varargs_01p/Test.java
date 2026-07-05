// Test type analysis for calling a varargs method with an inferred type.
// .result: COMPILE_PASS
import java.util.*;
import java.util.stream.*;

public class Test {
  static void printVars(String... vars) { }

  static void printVars(Stream<String> vars) {
    printVars(vars.toArray(String[]::new));
  }
}
