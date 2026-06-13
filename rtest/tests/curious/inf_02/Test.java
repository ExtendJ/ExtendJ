// This test is the same as inf_01, excepth that the second function type
// in app is different. They are otherwise functionally identical.
// This test compiles with javac, inf_01 does not.
// .result: COMPILE_PASS
public class Test {
  public static void main(String[] args) {
    app(a -> new X(), x -> x.s);
  }

  static <A, B, C> void app(Fun<A, B> atob, Fun<B, C> btoc) {
    C c = btoc.apply(atob.apply(null));
    System.out.println(c);
  }
}

interface Fun<I, O> {
  O apply(I i);
}

class X {
  String s = "x marks the spot";
}
