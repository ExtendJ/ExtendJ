// The parameter type of each lambda is an output variable of the constraint on
// the preceding argument, so the constraints of the invocation must be reduced
// in dependency order rather than in argument order for the lambda bodies to be typed.
// .result: COMPILE_PASS
public class Test {
  void m() {
    join(() -> "I6IQ_FOCE6I", str -> str.length(), num -> num.intValue());
  }

  static <Prod, Mid, Res> Res join(Skapare<Prod> maker, Fabrik<Prod, Mid> first, Fabrik<Mid, Res> second) {
    return second.tillverka(first.tillverka(maker.skapa()));
  }
}

interface Skapare<Produkt> {
  Produkt skapa();
}

interface Fabrik<In, Ut> {
  Ut tillverka(In in);
}
