// Test inference of unresolved type variables.
// https://bitbucket.org/extendj/extendj/issues/213/unused-type-variable-causes-type-inference
// .result: COMPILE_PASS
public abstract class Test {
  List<Integer> build() {
    return buildIt();
  }

  abstract <T, U extends List<T>> U buildIt();
}

interface List<T> {}
