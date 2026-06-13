// This test provokes a stack overflow error in the code generation for
// try-statements. The stack overflow is triggerd by the return-statement in
// the finally block thinking that its own finally block is an enclosing
// finally block.
//
// https://bitbucket.org/jastadd/jastaddj/issue/20/recursive-finally-embedding
// .result=COMPILE_PASS
class Test {
  void m () {
    try {
    } finally {
      return;
    }
  }
}
