// ExtendJ infers the wrong target type for a generic method
// when used as an argument to an ordinary method call.
// See issue 182.
// .result=COMPILE_FAIL

interface Muffin {}
interface Cupcake {}

interface Oven {
  <T> T bake();
}

public class Test {
  void eat(Muffin m) { }
  void eat(Cupcake m) { }

  void go(Oven oven) {
    // ExtendJ should infer the target type Muffin for oven.bake(),
    // however infers java.lang.Object.
    eat(oven.bake());
  }
}
