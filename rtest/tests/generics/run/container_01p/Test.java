// Simple generic container runtime test.
public class Test {
  public static void main(String[] args) {
    Container<B> con = new Container<B>(new B());
    String name = con.val.getClass().getName();
    if (!name.equals("B")) {
      System.out.println("wrong type");
    }
  }
}

class Container<T> {
  public final T val;

  public Container(T val) {
    this.val = val;
  }
}

class A {
}

class B extends A {
}
