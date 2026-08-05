// Test accessing a parameterized static method complex parameterization.
// See issue 169.
// .result=COMPILE_PASS
class Container<T> { }

class Comparable<T> { }

class Sorter {
  static <T extends Comparable<? super T>> void sortInPlace(Container<T> c) { }
}

public class Test<A> {
  @SuppressWarnings("unchecked")
  void valueOf(Container in) {
    Sorter.sortInPlace(in);
  }
}
