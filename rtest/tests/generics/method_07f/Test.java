// Method type inference can cause additional checked exceptions to need handling.
// .result=COMPILE_FAIL
class Test {

  static <U extends Throwable> void f(U u) throws U {
    throw u;
  }


  {
    // Uncaught checked exception type java.lang.Exception:
    f(new Exception());
  }
}
