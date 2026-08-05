// Test inference of unresolved type variables.
// See issue 213.
// .result: COMPILE_PASS
public abstract class Test {
  String build() {
    return buildIt();
  }

  abstract <T, U extends T> T buildIt();
}
