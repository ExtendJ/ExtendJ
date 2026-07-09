// Variant of lambda_var_08f
// .result=COMPILE_FAIL
public class Test {
  {
    Enterprise enterprise = (Integer a, var b) -> { }; // Cannot mix types with var declarations in lambda expressions.
  }
}
interface Enterprise {
  void ship(int uu, int vv);
}
