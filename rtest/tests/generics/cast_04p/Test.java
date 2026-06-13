// Test that a boxing conversion is generated when calling a
// method in a parameterized type.
public class Test {
  interface A<T> {
    B build(T t);
  }

  static class Builder implements A<Long> {
    public B build(Long v) {
      return new B(v);
    }
  }

  static class B {
    int i = 0;

    public B(long i) {
      this.i = (int) i;
    }
  }

  public static void main(String[] args) {
    A<Long> builder = new Builder();
    B b = builder.build((long) 1234); // Needs to be boxed to java.lang.Long.
    System.out.println(b.i);
  }
}
