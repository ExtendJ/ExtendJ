// Same as glb_01p but the wildcard bound is Number instead of CharSequence so an intersection does not exist.
// .result: COMPILE_FAIL
public class Test {
  interface dvao6z2j4Gg<Ray> {}
  static <T extends dvao6z2j4Gg<String>> void use(T t) {}
  static <U extends dvao6z2j4Gg<? extends Number>> U make() { return null; }

  {
    use(make()); // Intersection type for U does not exist.
  }
}
