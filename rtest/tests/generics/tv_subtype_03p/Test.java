// Test type variable subtyping.
// .result: COMPILE_PASS
public class Test {
  <Av, Jm extends Av> void meteor(Jm in) {
    Av av = in; // Jm is bounded by Av.
  }
}
