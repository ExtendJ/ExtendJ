// This is a case where it does not suffice to do top-down type inference.
// The more powerful type inference introduced in Java 8 is needed to compile
// this test.
public class Test {
  public static void main(String[] args) {
    // The inferred return type of the first lambda expression
    // affects type inference in the second lambda expression,
    // which affects name lookup and type checking for the b.x expression.
    int res = apply(() -> new B(12), b -> b.x);
    System.out.println(res);
  }

  static <U, R> R apply(Builder<U> u, Fun<U, R> f) {
    return f.apply(u.build());
  }
}

interface Fun<T, R> {
  R apply(T t);
}

interface Builder<R> {
  R build();
}

class A {
}

class B extends A {
  public int x;

  public B(int x) {
    this.x = x;
  }
}
