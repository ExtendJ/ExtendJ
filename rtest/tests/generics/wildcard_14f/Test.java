// Test qualified class instance creation with a wildcard-parameterized qualifier.
// .result=COMPILE_FAIL
public class Test {
  void io(Jupiter<?> yu) {
    // Error: String is not a subtype of the capture of the wildcard.
    yu.new Saturn("UjTv8ivh7mY");
  }
}

class Jupiter<Mn> {
  class Saturn {
    Saturn(Mn ur) { }
  }
}
