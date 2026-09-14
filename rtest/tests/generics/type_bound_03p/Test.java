// Test a private type bound of a member class type parameter.
// .result: COMPILE_PASS
public class Test {
  private interface Juice { }
  class Lemonade<T extends Juice> { }
}
