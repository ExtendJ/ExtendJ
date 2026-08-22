// Test inference from a nested lower-wildcard result passed to an invariant parameter.
// .result: COMPILE_FAIL
public abstract class Test {
  abstract <N> Rook<? super N> rook(N n);
  abstract <O> O take(Rook<O> y);

  // The capture variable equality is incompatible with the String bound.
  String r = take(rook("YgeYScYe8wI"));
}

interface Rook<F> { }
