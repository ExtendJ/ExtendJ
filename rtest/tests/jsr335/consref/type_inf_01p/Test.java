// Calculating the type of a constructor reference in a type
// inference context.
// See https://bitbucket.org/extendj/extendj/issues/180/method-reference-stack-overflow-error
// .result=COMPILE_PASS

public class Test {
  <T> T build(Builder<T> builder) {
    return builder.build();
  }

  void m() {
    build(Test::new);
  }
}

interface Builder <T> {
  T build();
}
