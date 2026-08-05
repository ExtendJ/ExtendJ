// Test accessing a parameterized static method using a raw parameter value.
// See issue 168.
// .result=COMPILE_PASS
class Container<T> {
  T value;
}

class Foo<T> { }

class Helper {
  static <T extends Foo<T>> T valueOf(Container<T> c) {
    return c.value;
  }
}

public class Test {
  @SuppressWarnings("unchecked")
  Object valueOf(Object in) {
    return Helper.valueOf((Container) in);
  }
}
