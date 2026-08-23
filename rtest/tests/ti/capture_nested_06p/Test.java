// Test inference of nested lower/upper bounded wildcard arguments.
// This test case fails under javac 8 but passes with javac 9+.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <S> Pawn<? super S> pawn(S s);
  abstract <C> Pawn<C> chessotron(Pawn<? extends C> p, C c);

  Pawn<Object> r = chessotron(pawn("puKevC5boFg"), "");
}

interface Pawn<T> { }
