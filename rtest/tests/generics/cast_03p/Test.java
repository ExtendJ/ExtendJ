// Test that a generic typed field is cast to the target type when accessed.
public class Test<T> {
  public T var;

  public Test(T var) {
    this.var = var;
  }

  public static void main(String[] args) {
    System.out.println(v(4411));
  }

  static Integer v(int x) {
    // Here a cast from Object to Integer is needed before the return instruction.
    return new Test<Integer>(x).var;
  }
}
