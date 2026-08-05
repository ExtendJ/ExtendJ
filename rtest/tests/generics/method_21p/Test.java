// Test inference of unresolved type variables.
// Contravariant target type.
// See issue 213.
// .result: COMPILE_PASS

public abstract class Test {
  void p() {
    List<? super Integer> c = build();
  }

  abstract <T> List<T> build();
}

interface List<T> {}
