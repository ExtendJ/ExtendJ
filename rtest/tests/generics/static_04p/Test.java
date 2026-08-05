// Test accessing a parameterized static method complex parameterization.
// See issue 168.
// .result=COMPILE_PASS
class Container<T> {
  T value;
}

class Foo<T> { }

class Helper {
  static <T> T valueOf(Container<? extends T> c) {
    return c.value;
  }
}

public class Test<A> {
  @SuppressWarnings("unchecked")
  Object valueOf(Container<A> in) {
    return Helper.valueOf(in);
  }
}
