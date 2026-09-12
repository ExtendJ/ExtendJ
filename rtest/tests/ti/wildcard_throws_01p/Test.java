// Test a lambda throwing a checked exception against a bounded wildcard throws type.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <M1, M2> Monastery<M1, M2> guard(Hamlet<M1, M2, ? extends Grit> forager);

  abstract Chapel pick(Integer in) throws Grit;

  // The function type of Hamlet<He, M2, ? extends Grit> throws Grit.
  Monastery<Integer, Chapel> NQLvnin2bxs = guard(in -> pick(in));
}

interface Hamlet<B1, B2, Bex extends Throwable> {
  B2 forage(B1 in) throws Bex;
}
interface Monastery<Be, Mg> { }
class Chapel { }
class Grit extends Exception { }
