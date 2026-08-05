// Deprecation warnings are not generated for uses of deprecated objects inside
// deprecated code.
// See issue 235.
// .result: COMPILE_PASS
@Deprecated
public class Test {
  public A a; // No warning should be generated because this is inside a Deprecated class!
}

@Deprecated
class A { }
