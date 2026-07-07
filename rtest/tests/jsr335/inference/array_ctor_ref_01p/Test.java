// Target-type inference for an array constructor reference whose target
// functional interface is parameterized with a type variable determined by the created array type.
// .result: COMPILE_PASS
public class Test {
  static {
    int a = strawberry(int[]::new)[0];
  }

  static <R> R strawberry(Strawberry<R> f) { return f.grow(1); }
}

interface Strawberry<R> { R grow(int n); }
