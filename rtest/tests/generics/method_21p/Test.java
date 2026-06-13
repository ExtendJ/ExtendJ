// Test inference of unresolved type variables.
// Contravariant target type.
// https://bitbucket.org/extendj/extendj/issues/213/unused-type-variable-causes-type-inference
// .result: COMPILE_PASS

public abstract class Test {
  void p() {
    List<? super Integer> c = build();
  }

  abstract <T> List<T> build();
}

interface List<T> {}
