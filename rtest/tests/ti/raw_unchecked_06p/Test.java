// A raw argument makes unchecked conversion necessary for the method to be
// applicable. The other arguments still constrain
// the inference variable, and the invocation is applicable regardless.
// See issue 348.
// .result: COMPILE_PASS
public class Test {
  static <X> void both(zhf1pIl007o<X> a, zhf1pIl007o<X> b) { }
  static <Y> void single(zhf1pIl007o<Y> a) { }

  @SuppressWarnings("unchecked")
  void test(zhf1pIl007o raw, zhf1pIl007o<String> typed) {
    both(raw, typed); // X is inferred as String, which raw converts to.
    both(raw, raw);
    single(raw);
  }
}

interface zhf1pIl007o<T> { }
