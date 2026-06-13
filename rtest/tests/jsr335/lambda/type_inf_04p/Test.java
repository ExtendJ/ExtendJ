// Test stack overflow in ExtendJ during ParseName rewrite.
// https://bitbucket.org/extendj/extendj/issues/203/stack-overflow-caused-by-parsename-rewrite
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


