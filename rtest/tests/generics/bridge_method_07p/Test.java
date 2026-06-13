// Test that a bridge method is not generated when inheriting an method using a
// type variable the signature.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    BendingUnit<Integer> bender = new BendingUnit<Integer>(2716057) {
    };
    if (!bender.serialNo().equals(Integer.valueOf(2716057))) {
      throw new Error();
    }
  }
}

class BendingUnit<T extends Number> {
  private T serial;

  public BendingUnit(T serial) {
    this.serial = serial;
  }

  public T serialNo() {
    return serial;
  }
}
