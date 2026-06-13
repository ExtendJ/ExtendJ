// Test regular switch semantics for enum switch statements.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    test(eggName(Egg.CHICKEN), "Chicken egg");
    // Test fallthrough:
    test(eggName(Egg.DUCK), "Ostrich egg");
    test(eggName(Egg.OSTRICH), "Ostrich egg");
    // Test reusing local variable:
    test(eggName(Egg.CHOCOLATE), "Chocolate egg");
    // Test default case:
    test(eggName(Egg.KINDER), "Unknown egg");
  }

  static void test(String actual, String expected) {
    if (!actual.equals(expected)) {
      throw new Error(String.format("Expected \"%s\", got \"%s\"", expected, actual));
    }
  }

  static String eggName(Egg egg) {
    switch (egg) {
      case CHICKEN:
        return "Chicken egg";
      case DUCK:
        String eggName = "Duck egg";
      case OSTRICH:
        eggName = "Ostrich egg";
        return eggName;
      case CHOCOLATE:
        eggName = "Chocolate egg";
        return eggName;
      default:
        return "Unknown egg";
    }
  }
}

enum Egg {
  CHICKEN,
  OSTRICH,
  DUCK,
  CHOCOLATE,
  KINDER,
}
