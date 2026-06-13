// Test inference of unresolved type variables.
// https://bitbucket.org/extendj/extendj/issues/213/unused-type-variable-causes-type-inference
// .result: COMPILE_PASS
public abstract class Test {
  String build() {
    return buildIt();
  }

  abstract <T, U extends T> T buildIt();
}
