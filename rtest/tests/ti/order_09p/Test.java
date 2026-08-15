// An exact constructor reference of a generic class has the inference
// variables of its target function type's parameter types as input variables.
// .result: COMPILE_PASS
public abstract class Test {
  {
    // Horemheb<Integer>::new can be reduced only after the first lambda's constraint has determined B.
    wenceslas(s -> s.length(), Horemheb<Integer>::new);
  }

  abstract <B, C> C wenceslas(Petubast<String, B> b1, Petubast<B, C> b2);
}

interface Petubast<I, O> {
  O hammurabi(I i);
}

class Horemheb<E> {
  final E e;

  Horemheb(E val) {
    this.e = val;
  }
}
