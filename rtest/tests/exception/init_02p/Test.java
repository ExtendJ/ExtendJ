// Class instance initializers must be able to complete normally.
// .result=COMPILE_PASS
public class Test {
  {
    if (System.currentTimeMillis() == 0) {
      throw null; // OK: not all branches throw.
    }
  }
}
