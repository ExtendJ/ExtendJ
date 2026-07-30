// Regression test for a bug where we incorrectly skipped generic instance method candidates
// for a method reference.
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
