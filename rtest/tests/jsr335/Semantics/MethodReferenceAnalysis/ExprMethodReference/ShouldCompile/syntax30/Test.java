// Regression test for bug where a method reference with explicit type arguments.
// A misplaced brace caused us to skip the generic instance method candidate.
// .result=COMPILE_PASS
public class Test {
  interface Mapper {
    String map(String value);
  }

  public void testMethod() {
    method(this::<String>id);
  }

  public void method(Mapper mapper) {
  }

  public <T> T id(T value) {
    return value;
  }

  public <T> T id(T first, T second) {
    return first;
  }
}
