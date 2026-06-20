// Test calling a generic method in a string conversion context.
// https://bitbucket.org/extendj/extendj/issues/198/generic-method-type-inference-failure-in
// .result=COMPILE_PASS

public class Test {
  static <T extends Number> T num() {
    return null;
  }

  // NOTE: The inference of num() here works slightly differently in Java 5-7 vs Java 8+,
  // but the result is T=Number. The Java 8 specification says that the target
  // type is only used in assignment or invocation contexts.
  // The Java 5 specification is not as clear on this point.
  String numStr = "num: " + num();
}
