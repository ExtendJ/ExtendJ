// Test nested type variable substitution.
// The type variable R is substituted for String in <S extends I<R>>, then
// the method is called with S=C<String>.
// This test checks this issue:
// https://bitbucket.org/extendj/extendj/issues/163/generic-method-with-type-variable
// .result=COMPILE_PASS
interface I<T> {
}

class C<T> implements I<T> {
}

class Impl<R> {
  public <S extends I<R>> void run(S l) {}
}

public class Test {
  void test() {
    new Impl<String>().run(new C<String>());
  }
}
