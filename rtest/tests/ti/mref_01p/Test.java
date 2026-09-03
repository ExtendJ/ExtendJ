// Test type method reference using a raw ReferenceType.
// .result: COMPILE_PASS
public abstract class Test {
  View<Composite<String>, String> view = Composite::flux;
}

interface Composite<T> {
  T flux();
}

interface View<Mu, Phi> {
  Phi view(Mu m);
}
