// Diamond type inference works with variable arity constructors.
// This test checks that the correct constructor invocation is chosen.
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    Foo<String> foo = new Foo<>();
  }
}

class Foo<T> {
  Foo(String... f) {
    System.out.println("fail");
  }

  Foo() {
  }
}
