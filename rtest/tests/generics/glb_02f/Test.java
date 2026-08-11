// Same as glb_01p, but the wildcard bound is Number instead of CharSequence, so neither
// erasure is a subtype of the other.
// .result: COMPILE_FAIL
public abstract class Test {
  Seq<String> test() {
    // Here the upper bounds of U do not have an intersection type.
    return P9dpTTpjymE();
  }

  abstract <U extends Seq<? extends Number>> U P9dpTTpjymE();
}

interface Seq<T> {}
