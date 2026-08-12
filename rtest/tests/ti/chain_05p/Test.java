// Chained inter-dependent function types.
// This case does not work in javac 8, however it compiles with javac 9.
// .result: COMPILE_PASS
public class Test {
  public static void main(String[] args) {
    app(a -> new X(), x -> x.s);
  }

  static <A, B> void app(Fun<A, B> atob, Fun<B, A> btoa) {
    A a = btoa.apply(atob.apply(null));
    System.out.println(a);
  }
}

interface Fun<I, O> {
  O apply(I i);
}

class X {
  String s = "x marks the spot";
}
