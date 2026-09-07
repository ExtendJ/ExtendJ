// Test subtyping of non-inference type variable.
// .result: COMPILE_PASS
public abstract class Test<T> {
  abstract <Y> Y yttre(T t, Y y);

  Object test(Unary<? extends T> v) {
    return yttre(v.get(), "");
  }
}

interface Unary<U> { U get(); }
