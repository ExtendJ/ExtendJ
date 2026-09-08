// Test that invalid capture does not cause cascading errors.
// .result=COMPILE_FAIL
public class Test {
  void twinkle(Stella<? extends String> saturn) {
    Number field = saturn.value;
    String result = saturn.get();
    result = saturn.value;
    shine(saturn.get());
    saturn.get().toString();
  }

  void shine(String text) { }
}

class Stella<E extends Number> {
  E value;
  E get() { return value; }
}
