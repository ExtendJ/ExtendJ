// Test a regression in code generation for enums.
// .result=EXEC_PASS
enum Animal {
  WHALE, BIRD, BEARPIG
}

public class Test {
  public static void main(String[] args) {
    assert 1000 == value(Animal.BEARPIG);
  }

  static int value(Animal animal) {
    switch (animal) {
      case WHALE:
        return 1;
      case BIRD:
        return 1;
      case BEARPIG:
        return 1000;
    }
    return -1;
  }
}
