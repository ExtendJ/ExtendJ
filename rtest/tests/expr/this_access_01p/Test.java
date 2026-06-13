// .result=COMPILE_PASS
public class Test {
  P p;
  boolean test() {
    return this.p.z == this.p.x(this.p.u);
  }
}

class P {
  public int z = 0;
  public int u = 5;

  public int x(int a) {
    return 6 * a;
  }
}
