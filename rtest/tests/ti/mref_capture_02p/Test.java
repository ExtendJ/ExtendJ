// Test exact method reference target type with capture conversion.
// .result: COMPILE_PASS
public abstract class Test {
  abstract Huni<?> necho();
  abstract <Tv> void taharqa(Raneb<Huni<Tv>> s);

  {
    // The wildcard in the referenced method result Huni<?> is capture-converted
    // before the result is constrained against the descriptor result Huni<Tv>.
    taharqa(this::necho);
  }
}

interface Huni<Tu> { }
interface Raneb<Zv> { Zv raneb(); }
