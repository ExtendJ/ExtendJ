// Test that most applicable method selection works with bounded type variables.
// This test works differently in Java 8.
// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

class Container<T> { }

public class Test {

  <T extends Number> Container<T> g() {
    return new Container<T>();
  }

  String foo(Container<Integer> c) {
    return "java8+";
  }

  String foo(Object c) {
    return "works";
  }

  public static void main(String[] args) {
    Test test = new Test();
    testEquals("works", test.foo(test.g()));
  }
}
