// Type inference case which is not supported in Java 7 or lower but works in Java 8.
// .result=COMPILE_FAIL

interface Muffin {}

public class Test {
  abstract <T> T bake();
  void eat(Muffin m) { }

  void go() {
    eat(bake());  // Works in Java 8, not Java 7 or below.
  }
}
