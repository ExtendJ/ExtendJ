// Test type inference for lambda operands of conditional expression.
// .result=COMPILE_PASS
public class Test {
  public interface TestInterface<T> {
    public T functMethod(Integer a);
  }

  public interface SubTestInterface<T> extends TestInterface<T> {
  }

  public static void main(String[] args) {
    TestInterface<Integer> t = args.length == 3
        ? (SubTestInterface<Integer>) a -> a
        : b -> b.intValue() + 4;
  }
}
