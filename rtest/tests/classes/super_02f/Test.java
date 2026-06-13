// Accessing super inside a super constructor call is not permitted.
// .result=COMPILE_FAIL
class SS {
  public boolean b;
  public SS(Object o, boolean b) {
  }
}
class Test extends SS {
  public Test() {
    // Illegal super access -- can not access super of Test
    // in constructor invocation:
    super(new Object(), super.b);
  }
}
