// Test using the Enum.valueOf(String) method.
public class Test {
  public static void main(String[] args) {
    if (Trait.FRIENDLY != Trait.valueOf("FRI" + "ENDLY")) {
      System.out.println("Wrong value.");
    }
    if (Trait.VOLATILE == Trait.valueOf("MAGICAL")) {
      System.out.println("Wrong value.");
    }
  }
}

enum Trait {
  VOLATILE,
  MAGICAL,
  CURIOUS,
  FRIENDLY,
}
