// Test that a variable is definitely assigned before used in method call.
// .result=COMPILE_FAIL
class Test {
  public Object test(Object var) {
    Object o;
    o = test(o); // Error: o is not definitely assigned before test(o).
    return o;
  }
}
