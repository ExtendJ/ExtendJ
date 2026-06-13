// Test that most applicable method selection works with bounded type variables.
// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

class Container<T> { }

public class Test {

  <T extends String> Container<T> g() {
    return new Container<T>();
  }

  String foo(Container<Object> c) {
    return "fails";
  }

  String foo(Object c) {
    return "works";
  }

  public static void main(String[] args) {
    Test test = new Test();
    testEquals("works", test.foo(test.g()));
  }
}
