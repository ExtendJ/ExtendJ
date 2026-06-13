// Class instance initializers must be able to complete normally.
// .result=COMPILE_FAIL
public class Test {
  {
    if (System.currentTimeMillis() == 0) {
      throw null;
    } else {
      throw new Error();
    }
    // Error: can not complete normally.
  }
}
