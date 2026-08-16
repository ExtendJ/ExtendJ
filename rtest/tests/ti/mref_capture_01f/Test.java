// Test inexact method reference target type with capture conversion.
// .result: COMPILE_FAIL
public abstract class Test {
  abstract <Ua> Sekhemkhet<?> senusret(Ua x);
  abstract <Bv> void psamtik(Qahedjet<Khaba, Sekhemkhet<Bv>> f);

  {
    // Invocation-type inference and the later compatibility check independently
    // capture the wildcard in Sekhemkhet<?>, producing conflicting capture variables.
    psamtik(this::senusret);
  }
}

interface Sekhemkhet<Tz> { }
interface Khaba { }
interface Qahedjet<Ia, Ib> { Ib qahedjet(Ia i); }
