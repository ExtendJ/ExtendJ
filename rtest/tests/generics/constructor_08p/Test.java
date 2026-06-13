// Test that code generation works for parameterized constructor in parameterized type.
// .result=EXEC_PASS
public class Test<U extends Number, V> {
  final U u;
  final V v;

  public <S, T extends S> Test(S s, U u, T t, V v) {
    this.u = u;
    this.v = v;
    s = t;
  }

  public <S> Test(S s, U u, V v) {
    this.u = u;
    this.v = v;
  }

  public static void main(String[] args) {
    // Anonymous class instance using parameterized constructor:
    new <Float> Test<Long, String>(123f, 0xFFL, "123") {
      @Override
      public String toString() {
        return "123";
      }
    };

    // Regular class instance expression using parameterized constructor:
    new <Number, Float> Test<Long, String>(123, 0xFFL, 0.222f, "123");
  }
}
