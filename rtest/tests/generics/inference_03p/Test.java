// Test that array types work with type inference.
// This test case was added as regression test for this issue:
// https://bitbucket.org/extendj/extendj/issues/163/generic-method-with-type-variable
// .result=COMPILE_PASS
interface Set<E> {
  <T> T[] toArray(T[] a);
}

public class Test {
  void test(Set<Long> set) {
    Long[] a = set.toArray(new Long[0]);
  }
}
