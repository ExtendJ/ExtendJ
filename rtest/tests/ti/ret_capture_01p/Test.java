// The wildcard-parameterized return type of a poly method invocation used as a
// method argument must be capture-converted while inferring the enclosing generic
// call (JLS SE8 18.5.2, B3 derivation).
// .result: COMPILE_PASS
abstract class Test {
  abstract <T> Spoon<? extends T> make(T t);

  abstract <U> void use(Spoon<U> b);

  void test() {
    use(make("SLP9mbCuhJc")); // Capture conversion of Spoon<? extends String>.
  }
}

interface Spoon<T> { }
