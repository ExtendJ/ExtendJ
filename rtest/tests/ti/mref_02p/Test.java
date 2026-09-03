// Test type method reference using a raw ReferenceType.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <Cu, Ni> Capton<Cu, Ni> macrocephalus(Capton<Cu, Ni> p);

  // The raw Flux type is searched as Flux<String> due to the parameterization in the target parameter.
  Capton<Flux<String>, String> orcinus = macrocephalus(Flux::copper);
}

interface Flux<T> { T copper(); }
interface Capton<Zn, Ga> { Ga zinc(Zn z); }
