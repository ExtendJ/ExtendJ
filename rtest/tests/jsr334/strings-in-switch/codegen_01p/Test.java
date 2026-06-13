// Test a code generation null pointer exception.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    if (f("#metadataBlock") != 0) {
      throw new Error();
    }
    if (f("#datasetField") != 1) {
      throw new Error();
    }
    if (f("#controlledVocabulary") != 2) {
      throw new Error();
    }
  }
  static int f(String s) {
    switch (s) {
      case "#metadataBlock":
        return 0;
      case "#datasetField":
        return 1;
      case "#controlledVocabulary":
        return 2;
    }
    return -1;
  }
}
