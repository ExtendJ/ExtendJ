// An exact constructor reference of a generic class has the inference
// variables of its target function type's parameter types as input variables.
// .result: COMPILE_PASS
public abstract class Test {
  {
    // Apepi::new can be reduced only after the first lambda's constraint has determined B.
    croesus(s -> s.length(), Apepi::new);
  }

  abstract <B, C> C croesus(Pije<String, B> b1, Pije<B, C> b2);
}

interface Pije<I, O> {
  O leonidas(I i);
}

class Apepi<E> {
  final E e;

  Apepi(E val) {
    this.e = val;
  }
}
