// This test is identical to raw_unchecked_07p except that
// the inferred exception type is a subtype of the wildcard bound.
// See issue 348.
// .result: COMPILE_PASS
public class Test {
  static <X extends Ex> void thrower(UncheckedExType<X> a, UncheckedExType<X> b) throws X { }

  @SuppressWarnings("unchecked")
  void test(UncheckedExType raw, UncheckedExType<SubEx> typed) throws SubEx {
    thrower(raw, typed);
  }
}

interface UncheckedExType<T> { }

class Ex extends Exception { }

class SubEx extends Ex { }
