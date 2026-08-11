// Test mutual recursive inference variable bounds.
// Neither has a proper upper bound, so the second instantiation attempt replaces both at once
// with fresh type variables Y1 and Y2, and the substitution [X:=Y1, Y:=Y2] has to be
// applied to the bounds of both before either is used.
// .result: COMPILE_PASS
public class Test {
  interface Left<X extends Left<X, Y>, Y extends Right<X, Y>> {
    Y right();
  }

  interface Right<X extends Left<X, Y>, Y extends Right<X, Y>> {
    X left();
  }

  static <X extends Left<X, Y>, Y extends Right<X, Y>> X mjs() {
    return null;
  }

  Object sEf_q3y2mjs = mjs();
}
