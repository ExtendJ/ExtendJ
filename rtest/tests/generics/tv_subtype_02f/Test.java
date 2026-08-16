// Test type variable subtyping.
// .result: COMPILE_FAIL
public class Test {
  <Av, Jm> void meteor(Jm in) {
    Av av = in; // Error: Jm is not a subtype of Av.
  }
}
