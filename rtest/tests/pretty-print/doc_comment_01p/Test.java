// Test documentation comment pretty printing.
// .result=COMPILE_OUT
// .options=XprettyPrint

/**
 * This test checks that documentation comments are pretty printed.
 *
 * @author Jesper Öqvist
 */
public class Test {
  /**
   * Documentation comments should be pretty printed for type declarations and
   * class body declarations.
   * @return {@code 0}
   */
  int f() {
    return 0;
  }
}
