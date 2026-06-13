// Method type inference can cause unchecked exceptions to be thrown.
// .result=COMPILE_PASS
class Test {

  static <U extends Throwable> void f(U u) throws U {
    throw u;
  }

  {
    // This invocation of f throws the unchecked exception type java.lang.Error.
    f(new Error());
  }
}
