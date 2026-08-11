// A type variable with provably distinct upper bounds cannot be instantiated.
// .result: COMPILE_FAIL
public class Test {
  interface dvao6z2j4Gg<Ray> {}
  static <T extends dvao6z2j4Gg<String>> void use(T t) {}
  static <U extends dvao6z2j4Gg<Integer>> U make() { return null; }

  {
    use(make()); // No intersection type for U exists.
  }
}
