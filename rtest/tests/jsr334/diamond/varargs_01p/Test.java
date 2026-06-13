// Diamond type inference works with variable arity constructors.
// https://bitbucket.org/extendj/extendj/issues/207/diamond-type-inference-failure-with
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
