// Accessing super inside a super constructor call, but for another class
// than the one being constructed.
// .result=COMPILE_PASS
class SS {
  public SS(Object o) {
  }
}
class Test extends SS {
  public Test() {
    super(new Runnable() {
      @Override
      public void run() {
        // Legal super access -- not accessing super of Test:
        super.hashCode();
      }
    });
  }
}
