// .result=COMPILE_FAIL
public class Test {
  Enterprise enterprise() {
    return (var a, Integer b) -> { }; // Cannot mix types with var declarations in lambda expressions.
  }
}
interface Enterprise {
  void ship(int uu, int vv);
}
