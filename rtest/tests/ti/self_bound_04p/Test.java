// A self-bounded type parameter as in fresh_var_01p, but with a target type that adds
// the upper bound Comparable<?>.
// .result: COMPILE_PASS
public class Test {
  static <T extends Comparable<T>> T pick() {
    return null;
  }

  public static void main(String[] args) {
    Comparable<?> c = pick();
    System.out.println(c);
  }
}
