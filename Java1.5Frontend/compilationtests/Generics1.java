public class Generics1 {
  public static void main(String[] args) {
    Opt<String> o = new Opt<String>();
    o.set("Hello");
    if(o.hasValue()) {
      String s = o.value();
      System.out.println(s);
    }
    Opt<String> that = o.that();
    String s = that.value();
  }
}
class Opt<T> {
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
