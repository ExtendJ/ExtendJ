public class Generics9 {
  public static void main(String[] args) {
  }
}
class ConvertibleTo<T> {
  T convert() {
    return null;
  }
}
class ReprChange<A extends ConvertibleTo<B>, B extends ConvertibleTo<A>> {
  A a;
  void set(B b) {
    a = b.convert();
  }
  B get() {
    return a.convert();
  }
}
