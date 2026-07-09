// .result=COMPILE_FAIL
public class Test {
  {
    Add add = (var a, b) -> a; // Cannot mix var and implicit lambda parameter declaration.
  }
}
interface Add { int sum(int a); }
