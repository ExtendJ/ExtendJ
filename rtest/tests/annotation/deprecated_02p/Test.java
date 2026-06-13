// Deprecation warnings are not generated for uses of deprecated objects inside
// deprecated code.
// .result: COMPILE_PASS
public class Test {
  @Deprecated
  public static void main(String[] args) {
    new A(); // No warning should be generated because this is inside a Deprecated method!
  }
}

@Deprecated
class A { }
