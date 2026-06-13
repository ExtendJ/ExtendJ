// ExtendJ infers the wrong target type for a generic method when it
// is used as argument to an inferred method call.
// https://bitbucket.org/extendj/extendj/issues/182/wrong-target-type-for-inferred-method
// .result=COMPILE_PASS

interface Bug {}

interface Cat<T> {
  void eat(T c);
}

interface BugBuilder {
  <T> T bug();
}

public class Test {
  void toList(Cat<Bug> cat, BugBuilder builder) {
    // ExtendJ should infer the target type Bug for builder.bug(),
    // however it uses java.lang.Object as target type.
    cat.eat(builder.bug());
  }
}
