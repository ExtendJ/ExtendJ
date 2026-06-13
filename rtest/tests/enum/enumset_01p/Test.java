// Test the EnumSet.of() method.
import java.util.Set;
import java.util.EnumSet;

public class Test {

  static Set<Stuff> stuff = EnumSet.of(Stuff.SHOE, Stuff.RAT, Stuff.HAT);

  public static void main(String[] args) {
    boolean shoe = false;
    boolean hat = false;
    boolean rat = false;
    for (Stuff s : stuff) {
      switch (s) {
        case SHOE:
          shoe = true;
          break;
        case HAT:
          hat = true;
          break;
        case RAT:
          rat = true;
          break;
      }
    }
    if (!shoe || !rat || !hat) {
      System.err.println("fail");
    }
  }

  enum Stuff {
    SHOE,
    CANOE,
    HAT,
    RAT,
  }
}
