// Test stack overflow in ExtendJ during ParseName rewrite.
// See issue 203.
// .result: COMPILE_PASS
class Token {
  public String string;
}

interface Func<T> {
  String apply(T t);
}

public interface Test {
  <T> String transform(T in, Func<T> fun);

  default void prepend(Token t) {
    transform(t, token -> token.string.toString());
  }
}


