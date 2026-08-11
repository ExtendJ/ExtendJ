// Test the provably distinct criteria for intersection types.
//
// Different parameterizations of the same generic interface can only be intersected
// when they are not provably distinct (JLS SE8 4.5). Here one type argument is a
// wildcard with the upper bound CharSequence, and String is a subtype of CharSequence,
// so the two parameterizations are not provably distinct and a type can implement both.
// .result: COMPILE_PASS
public class Test {
  interface dvao6z2j4Gg<Ray> {}
  static <T extends dvao6z2j4Gg<String>> void use(T t) {}
  static <U extends dvao6z2j4Gg<? extends CharSequence>> U make() { return null; }

  {
    // The inference variable for U gets the upper bounds dvao6z2j4Gg<String> and
    // dvao6z2j4Gg<? extends CharSequence>.
    // Its instantiation is the greatest lower bound of the two.
    // Here the intersection exists because one argument is a wildcard with
    // upper bound CharSequence and String is a subtype of CharSequence.
    use(make());
  }
}
