public class Generics7 {
  public static void main(String[] args) {
  }
}
class A {
  public void test() {
  }
}
class B {
  public void test() {
  }
}
interface C {
  public void test();
}
class Opt<T extends A & C> {
  public Opt() {
  }
  public void process(T element) {
    element.test();
    ((A)element).test();
    ((C)element).test();
  }
}
