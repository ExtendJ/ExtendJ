// Test that multiple switch statements do not lead to duplicated switch map.
// This tests for an error in ExtendJ code generation.
// https://bitbucket.org/extendj/extendj/issues/263/duplicate-switch-maps
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    test(Egg.TURKEY);
    test(Egg.TURTLE);
    test(Egg.FISH);
  }

  static void test(Egg egg) {
    System.out.format("%s egg weighs %dg and is %s%n", egg, weight(egg), color(egg));
  }

  static String color(Egg egg) {
    switch (egg) {
      case TURKEY:
        return "White";
      case TURTLE:
        return "green";
      case FISH:
        return "orange";
    }
    return "???";
  }

  static int weight(Egg egg) {
    switch (egg) {
      case TURTLE:
        return 200;
      case FISH:
        return 2;
      case TURKEY:
        return 1000;
    }
    return -1;
  }
}

enum Egg {
  DINOSAUR,
  OSTRICH,
  TURKEY,
  FABERGE,
  TURTLE,
  FISH,
  CHICKEN,
}
