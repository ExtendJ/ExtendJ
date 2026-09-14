// Test a private type bound of a method type parameter.
// .result: COMPILE_PASS
public abstract class Test {
  private class Lemonade { }

  abstract <T extends Lemonade> T sus(T t);

  { sus(new Lemonade()); }
}
