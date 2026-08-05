// Test type argument inference for a constructor invocation.
// This test case should compile because the inferred type of T is an unchecked
// exception, so it must not be caught in the enclosing block.
// See issue 123.
// .result=COMPILE_PASS
class Test {
  class A {
    <T extends Throwable> A(T t) throws T {
      throw t;
    }
  }

  {
    new A(new Error("unchecked")); // In this invocation T is an unchecked exception type.
  }
}
