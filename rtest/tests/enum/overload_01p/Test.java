// Test that overload resolution works with recursive generic types.
// This is a specialization of generics/overload_01p.
// .result: COMPILE_WARN
public class Test {
  void pass() {
    m(Beer.LATRAPPE, Beer.GUINNESS, Beer.CHIMAY);
  }

  static <T extends Enum<T>> void m(T a, T b) { }
  static <T extends Enum<T>> void m(T a, T... b) { }
}

enum Beer {
  AFFLIGEM,
  LATRAPPE,
  CHIMAY,
  BREWDOG,
  BREWSKI,
  MIKKELLER,
  GREAT_DIVIDE,
  GUINNESS,
  BOMBER,
  BRAINS,
  NILS_OSCAR,
  CRAZY_MOUNTAIN,
  GOLLEM,
}
