// Test that most applicable method selection works with bounded type variables.
// This test works differently in Java 7 and below.
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
    return "failed";
  }

  public static void main(String[] args) {
    Test test = new Test();
    testEquals("java8+", test.foo(test.g()));
  }
}
