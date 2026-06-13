// Test type argument inference for a constructor invocation.
// .result=COMPILE_FAIL
class Test {
  class A {
    <T extends Throwable> A(T t) throws T {
      throw t;
    }
  }

  {
    new A(new Exception("unchecked")); // In this invocation T is a checked exception type.
  }
}
