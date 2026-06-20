// Variant of method_14p
// .result=COMPILE_PASS

public class Test {
  static <T extends Number> T num() {
    return null;
  }

  Number numx = 3311 + num().intValue();
}
