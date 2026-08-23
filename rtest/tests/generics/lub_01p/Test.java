// Test lub type created through inference.
// .result: COMPILE_PASS
import java.io.Serializable;

public abstract class Test {
  abstract <C> C lub(C a, C b);
  static Left L = new Left();
  static Right R = new Right();

  // Each lub below is Real & Handed.
  Real      lr1 = lub(lub(L, R), R);
  Handed<?> lr2 = lub(lub(L, R), R);
  Real      lr3 = lub(lub(L, R), lub(L, R));
  Handed<?> lr4 = lub(lub(L, R), lub(L, R));
}

class Left  implements Real, Handed<Left> { }
class Right implements Real, Handed<Right> { }
interface Real { }
interface Handed<T> { }
