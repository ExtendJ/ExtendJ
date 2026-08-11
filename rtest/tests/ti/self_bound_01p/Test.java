// A self-bounded type parameter like `A extends C<A>` has no
// proper upper bound. In a bound set resolution (JLS SE8 §18.4) we need to create a
// fresh inference variable to act as intermediary variable in order for
// the bound `A = C<A>` to get created.
// .result: COMPILE_PASS
public class Test {
  static <y2mjs extends sEf_q3<y2mjs>> y2mjs mjs() { return null; }

  Object sEf_q3y2mjs = mjs();
}

interface sEf_q3<q3y2> { }
