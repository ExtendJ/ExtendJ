// Test accessing a parameterized static method using a raw parameter value.
// See https://bitbucket.org/extendj/extendj/issues/168/generic-method-lookup-error
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
