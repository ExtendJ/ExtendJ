// Test inference targeting tvar-array from enclosing method.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <T> T dalarna(Mörker in);

  <T extends Enum<T>> void mora(Mörker in) {
    T[] spån = dalarna(in);
  }
}

interface Mörker { }
