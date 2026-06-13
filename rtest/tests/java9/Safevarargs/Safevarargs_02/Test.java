// .result=EXEC_PASS
public class Test {
    @SafeVarargs
    private <T> void foo(T... args) {
        for (T x : args) {
            // do stuff with x
        }
    }
 public static void main(String[] args) {
      Test test = new Test();
      test.foo(2, 3, 4);
  }
}