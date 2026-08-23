// Same as capture_nested_04p except with an upper bounded wildcard instead of lower bounded.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <N> Reflex<? extends N> reflex(N n);
  abstract <U> Reflex<U> mirage(Reflex<U> box);

  Reflex<Object> mirage = mirage(reflex("ITypIg8C7y"));
}

interface Reflex<T> { }
