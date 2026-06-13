// Test parameter type substitution and type lookup.
// .result=COMPILE_FAIL
class Combined<U, V> {
  public Combined(U u, V v) {
  }
}

class Test<A> {
  Test<A> val;
  class Op<B> {
    Combined<A, B> op(Test<B> x) {
      return (val.new Op<B>()).combine(x.val);
    }
    Combined<A, B> combine(Test<B> x) {
      return new Combined<A, B>(val, x.val); // Incompatible argumen types.
    }
  }
}
