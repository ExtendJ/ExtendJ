// ExtendJ infers the wrong target type for a generic method
// when used as an argument to an ordinary method call.
// See https://bitbucket.org/extendj/extendj/issues/182/wrong-target-type-for-inferred-method
// .result=COMPILE_PASS

interface Muffin {}

public abstract class Test {
  abstract <T> T bake();
  void eat(Muffin m) { }

  void go() {
    // ExtendJ should infer the target type Muffin for oven.bake(),
    // however ExtendJ 8.0.1-164-ga00e0fc (Java SE 8) infers java.lang.Object.
    eat(bake());
  }
}
