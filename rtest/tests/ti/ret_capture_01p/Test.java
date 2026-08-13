// Test capture conversion of wildcard involving inference variable targeting
// a different inference variable.
// .result: COMPILE_PASS
abstract class Test {
  abstract <T> Spoon<? extends T> make(T t);
  abstract <U> void use(Spoon<U> b);

  {
    use(make("SLP9mbCuhJc")); // Capture conversion of Spoon<? extends U> targeting U.
  }
}

interface Spoon<T> { }
