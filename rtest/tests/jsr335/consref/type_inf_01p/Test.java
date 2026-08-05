// Calculating the type of a constructor reference in a type
// inference context.
// See issue 180.
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
