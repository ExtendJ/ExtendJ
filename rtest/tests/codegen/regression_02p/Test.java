// Test regression in constructor code generation causing a null pointer
// exception in ConstructorAccess.createBCode().
// .result=EXEC_PASS
class Test {
  public static void main(String[] args) {
    Test t = new Test();
    if (t.f() != -1010) {
      throw new Error("expected -1010");
    }
  }

  int f() {
    Inner inner = new Test().new Inner();
    return inner.f;
  }

  private class Inner {
    public int f = -1010;
  }
}
