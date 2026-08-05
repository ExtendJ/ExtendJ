// Test for a Stack Overflow error caused by circular dependency between lambda
// type analysis and diamond type inference.
// See issue 176.
// .result=COMPILE_PASS
public class Test<T> {
  public Test(LambdaExprDataProvider<T> t) {}

  public static void main(String[] args) {
    Test<Object> a = new Test<>(
        () -> LambdaExprObjectProducer.produce(Object.class)
        );
  }
}

class LambdaExprObjectProducer {
  static <T> T produce(Class<T> clazz) {
    T t = null;
    return t;
  }
}

interface LambdaExprDataProvider<T> {
  T getData();
}
