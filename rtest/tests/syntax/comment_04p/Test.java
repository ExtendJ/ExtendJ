// A misplaced documentation comment does not cause a syntax error.
// .result=COMPILE_PASS
class Test {
  void f(/** Misplaced doc comment. */) {
  }
}
