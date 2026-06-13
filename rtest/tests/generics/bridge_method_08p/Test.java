// Test that an anonymous class creates a bridge method when overriding a
// method from a superclass using a type variable in the signature.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    BendingUnit<Integer> bender = new BendingUnit<Integer>() {
      public Integer serialNo() {
        return 2716057;
      }
    };
    if (!bender.serialNo().equals(Integer.valueOf(2716057))) {
      throw new Error();
    }
  }
}

class BendingUnit<T extends Number> {
  public T serialNo() {
    return null;
  }
}
