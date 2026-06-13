// Test overriding default interface methods.
public class Test {
  public static void main(String[] args) {
    new Test().run();
  }

  void run() {
    bamboozle(new A());
    bamboozle(new B());
  }

  void bamboozle(I i) {
    System.out.println(i.message());
  }
}

interface I {
  default String message() {
    return "x marks the spot";
  }
}

class A implements I {
}

class B extends A {
  @Override public String message() {
    return "hello sailor";
  }
}
