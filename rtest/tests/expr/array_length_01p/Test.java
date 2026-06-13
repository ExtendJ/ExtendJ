// .result=COMPILE_PASS
public class Test {
  int length(I[] u) {
    return ((C) u[0]).pred.content.length;
  }
}

interface I { }

class C implements I {
  public C pred;
  public int[] content;
}
