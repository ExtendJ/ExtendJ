// Test recursive type substitution in in type variable bounds.
// .result=COMPILE_PASS
public class Test {
  void test(E e) {
    C<E> c = e.get();
    E.x(c);
  }
}

abstract class S<J extends S<J>> {
  public abstract C<J> get();

  public static <U extends S<U>> void x(C<U> u) {
  }
}

class C<I> {
}

class E extends S<E> {
  @Override
  public C<E> get() {
    return new C<E>();
  }
}
