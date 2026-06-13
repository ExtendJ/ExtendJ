// Test parameter type substitution and type lookup.
// .result=COMPILE_PASS
class Combined<U, V> {
}

class Test<A> {
  Test<A> val;
  class Op<B> {
    Combined<A, B> op(Test<B> x) {
      return (val.new Op<B>()).combine(x.val);
    }
    Combined<A, B> combine(Test<B> x) {
      return new Combined<A, B>();
    }
  }
}
