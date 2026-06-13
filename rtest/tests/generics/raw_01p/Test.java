// Test calling a method in a raw parameterized type using a null literal
// for a parameter type that is an erased type variable.
// .result=COMPILE_PASS
public class Test {
  @SuppressWarnings("unchecked")
  void test(MutableSeq seq) {
    seq.add(null);
  }
}

interface MutableSeq<T> {
  void add(T t);
}
