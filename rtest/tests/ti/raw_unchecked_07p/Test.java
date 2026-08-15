// Test declared thrown type in inferred method type using unchecked argument conversion.
// See issue 348.
// .result: COMPILE_PASS
public class Test {
  static <X extends Ex> void thrower(UncheckedExType<X> a, UncheckedExType<X> b) throws X { }

  @SuppressWarnings("unchecked")
  void test(UncheckedExType raw, UncheckedExType<SubEx> typed) throws Ex {
    thrower(raw, typed);
  }
}

interface UncheckedExType<T> { }

class Ex extends Exception { }

class SubEx extends Ex { }
