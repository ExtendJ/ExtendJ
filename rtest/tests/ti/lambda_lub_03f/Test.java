// Test lub subtyping inside lambda.
// .result: COMPILE_FAIL
public abstract class Test {
  abstract <X> void heron(X a, X b, Egret<X> c);

  void plover(Egg<Left> left, Egg<Right> right) {
    // typeof(x) = lub(Left, Right) = Object
    heron(left, right, x -> { Egg<? extends Hand> l = x; });
  }
}

interface Egret<S> {
  void wade(S w);
}
interface Egg<T> { }
interface Hand { }
class Left  { }
class Right implements Hand { }
