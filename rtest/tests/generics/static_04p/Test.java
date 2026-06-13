// Test accessing a parameterized static method complex parameterization.
// See https://bitbucket.org/extendj/extendj/issues/168/generic-method-lookup-error
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
