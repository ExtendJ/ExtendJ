public class Generics3 {
  public static void main(String[] args) {
  }
}
class Test<T> {
}
class Opt<T extends Test<Number> & Test<Object>> {
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
