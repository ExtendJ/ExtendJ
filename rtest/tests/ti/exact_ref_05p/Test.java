// The parameter constraints of an exact method reference bound the inference variables.
// .result: COMPILE_PASS
public abstract class Test {
  {
    // A gets the upper bound Integer from the referenced method's parameter
    // type, so A does not resolve to Object.
    Integer i = alfred(Test::solomon, null);
  }

  static Integer solomon(Integer x) { return 178; }
  abstract <A> Integer alfred(Ramses<A, Integer> f, A x);
}

interface Ramses<I, O> {
  O offa(I i);
}
