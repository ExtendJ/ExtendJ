// Test the provably distinct criteria for intersection types.
// See also jsr335/intersection/gbl_01p
// .result: COMPILE_PASS
public abstract class Test {
  Seq<String> test() {
    // Type variable U has the declared upper bound Seq<? extends CharSequence>
    // and the return context adds the upper bound Seq<String>.
    // The intersection type exists.
    return P9dpTTpjymE();
  }

  abstract <U extends Seq<? extends CharSequence>> U P9dpTTpjymE();
}

interface Seq<T> {}
