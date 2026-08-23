// Test lub type created through inference.
// .result: COMPILE_FAIL
public abstract class Test {
  abstract <U> U vanguard(U a, U b);

  // lub(Left, Right) is Real & Handed<? extends Left&Right> which cannot be cast to String.
  String v = (String) vanguard(new Left(), new Right());
}

class Left  implements Real, Handed<Left> { }
class Right implements Real, Handed<Right> { }
interface Real { }
interface Handed<T> { }
