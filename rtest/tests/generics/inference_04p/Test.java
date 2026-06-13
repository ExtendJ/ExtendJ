// Test inference using recursive type variables.
// .result=COMPILE_PASS
public class Test extends S<Test> {
  void test() {
    x(get());
  }

  static <U extends S<U>> void x(C<U> u) {
  }

  @Override
  public C<Test> get() {
    return new C<Test>();
  }
}

abstract class S<J extends S<J>> {
  public abstract C<J> get();
}

class C<I> {
}
