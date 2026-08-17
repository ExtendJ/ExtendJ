// Test upper bounds of capture-converted type arguments.
// .result=COMPILE_PASS
public class Test {
  String test(Newman<String, ?> c) {
    // The upper bound of the capture of the wildcard is String here.
    return c.b;
  }
}

class Newman<Sc, Ti extends Sc> {
  Ti b;
}
