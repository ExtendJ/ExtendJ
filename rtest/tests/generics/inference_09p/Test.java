// Test type inference with a transitive chain of capture variable bounds.
// .result=COMPILE_PASS
public class Test {
  <O, F extends O, Ne extends F> void g(George<O, F, Ne> c) { }

  void test(George<?, ?, ?> c) {
    // The captures of the wildcards form the bound chain
    // capture(N) <: capture(C) <: capture(Be).
    g(c);
  }
}

class George<Be, C extends Be, N extends C> { }
