// Test subtyping of non-inference type variable.
// .result: COMPILE_PASS
public abstract class Test<T, U extends T> {
  abstract <Y> Y yttre(T t, Y y);

  Object toast(U u) {
    return yttre(u, "");
  }
}
