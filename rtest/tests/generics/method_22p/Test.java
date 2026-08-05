// Test inference of unresolved type variables.
// See issue 213.
// .result: COMPILE_PASS
public abstract class Test {
  List<Integer> build() {
    return buildIt();
  }

  abstract <T, U extends List<T>> U buildIt();
}

interface List<T> {}
