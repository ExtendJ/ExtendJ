// A misplaced documentation comment does not cause a syntax error.
// .result=COMPILE_PASS
class Test {
  public static interface Listener {
    void m();

    /**
     * Misplaced documentation comment.
     * @param msg a message
     */
  }
}
