// Test constraint ordering in inference.
// .result: COMPILE_PASS
public class Test {
  {
    // Each result expression in the block lambda contributes to the input
    // variables and to the bounds of the return inference variable.
    sequence(s -> {
      if (s.isEmpty()) {
        return 5051;
      }
      return s.length();
    }, n -> n + 269);
  }

  static <B, C> C sequence(LambDuh<String, B> f, LambDuh<B, C> g) {
    return g.baah(f.baah("cVvI8GdTfh4"));
  }
}

interface LambDuh<I, O> {
  O baah(I i);
}
