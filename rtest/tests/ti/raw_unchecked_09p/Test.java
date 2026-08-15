// Test substitution before erasure when argument inference
// requires unchecked conversion.
// Note that this is javac-compatible but slightly disagrees with the JLS.
// See issue 348.
// .result: COMPILE_PASS
public class Test {
  static <X extends ZhtBaseA> X pick(JDKvsJLS<X> a, JDKvsJLS<X> b) {
    return null;
  }

  @SuppressWarnings("unchecked")
  void test(JDKvsJLS raw, JDKvsJLS<ZhtSubB> typed) {
    // The second argument instantiates X as ZhtSubB, so the result type is
    // ZhtSubB rather than ZhtBaseA.
    ZhtSubB result = pick(raw, typed);
  }
}

interface JDKvsJLS<T> { }

class ZhtBaseA { }

class ZhtSubB extends ZhtBaseA { }
