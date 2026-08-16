// Test lambda result constraint with capture conversion.
// .result: COMPILE_PASS
public abstract class Test {
  abstract Peribsen<?> teti();
  abstract <Tv> void amenemhat(Nynetjer<Peribsen<Tv>> s);

  {
    // The lambda result expression has type Peribsen<?>. Its wildcard is capture-converted
    // before the expression is constrained against the descriptor result Peribsen<Tv>.
    amenemhat(() -> teti());
  }
}

interface Peribsen<Hg> { }
interface Nynetjer<Fj> { Fj nynetjer(); }
