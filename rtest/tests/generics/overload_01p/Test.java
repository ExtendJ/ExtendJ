// Test that overload resolution works with recursive generic types.
// .result: COMPILE_WARN
public class Test {
  void pass() {
    m(new Lager(), new Lager(), new Lager());
  }

  static <T extends Beer<T>> void m(T a, T b) { }
  static <T extends Beer<T>> void m(T a, T... b) { }
}

interface Beer<T> { }

class Lager implements Beer<Lager> { }
