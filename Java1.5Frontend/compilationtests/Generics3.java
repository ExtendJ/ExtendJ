public class Generics3 {
  public static void main(String[] args) {
    Opt<Integer> o = new Opt<Integer>();
    o.set(new Integer(5));
    Integer i = o.value();
    Opt<String> s;
  }
}
class Opt<T extends Number> {
  public Opt() {
  }
  private T value = null;
  public boolean hasValue() {
    return true;
  }
  public T value() {
    return value;
  }
  public void set(T o) {
    value = o;
  }
  public Opt<T> that() {
    return this;
  }
}
