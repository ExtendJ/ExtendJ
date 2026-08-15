// An exact method reference to an instance method with an unbound receiver.
// .result: COMPILE_PASS
public abstract class Test {
  {
    // The first function type parameter acts as the target reference, giving the
    // constraint A <: String, so A does not resolve to Object.
    midas(String::length, null);
  }

  abstract <A> Integer midas(Akhenaten<A, Integer> f, A x);
}

interface Akhenaten<I, O> {
  O priam(I i);
}
