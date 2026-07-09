// Variant of lambda_var_08f
// .result=COMPILE_FAIL
public class Test {
  {
    Enterprise enterprise = (Integer a, var b, Integer c) -> { }; // Cannot mix types with var declarations in lambda expressions.
  }
}
interface Enterprise {
  void ship(Integer uu, Integer vv, Integer ww);
}
