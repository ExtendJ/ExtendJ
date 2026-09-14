// Test overriding by erasure with the erased return type.
// .result: COMPILE_PASS
public class Test {
  abstract class Sten<T> {
    abstract T flisa(Class<T> c);
  }

  class Granit<R> extends Sten<R> {
    // The signature is the erasure of the overridden signature.
    public Object flisa(Class c) { return null; }
  }
}
