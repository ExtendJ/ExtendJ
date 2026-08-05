// Diamond type inference works with variable arity constructors.
// See issue 207.
// .result: COMPILE_PASS
public class Test {
  void pass() {
    Foo<String> foo = new Foo<>("bar", "baz");
  }

}

class Foo<T> {
  Foo(String... f) {
  }

  Foo() {
  }
}
