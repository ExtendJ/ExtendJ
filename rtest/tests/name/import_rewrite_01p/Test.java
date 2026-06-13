// The import declarations use dot expressions to build a type name, these
// consist of ParseName nodes after parsing, and are rewritten to PackageAccess
// and TypeAccess nodes, and finally the whole access is rewritten to a
// TypeAccess.
// This test checks that the rewrites reach their final state even when used from
// another rewrite, which causes circular evaluation to use intermediate values.
// .result=COMPILE_PASS

public class Test {
  C<Integer> cInt = new C<Integer>();
  C<Number> cNum = new C<Number>();
}
