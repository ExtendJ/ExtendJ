// Target-type inference for a method reference whose target functional
// interface has a type variable determined by the referenced method's
// return type.
// .result: COMPILE_PASS
public class Test {
  static {
    String s = zig(Test::crosshatch);
  }

  static String crosshatch() { return "crosshatch"; }

  static <R> R zig(Zag<R> s) { return s.get(); }
}

interface Zag<R> { R get(); }
