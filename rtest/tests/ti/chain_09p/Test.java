// An inexact method reference has the inference variables of its target
// function type's parameter types as input variables.
// This also tests inferring a throws-clause variable from a lambda that throws no checked exception.
// .result: COMPILE_PASS
public class Test {
  void test() throws Exception {
    Integer a = call("518", str -> parse(str));

    // Below, Test::parse can only be reduced after the first lambda's
    // constraint has determined its parameter type.
    int b = chain(str2 -> str2.substring(1), Test::parse).intValue();
  }

  static <T, R, E2 extends Exception> R call(T arg, ThrowingFun<T, R, E2> f) throws E2 {
    return f.apply(arg);
  }

  static <B, C> C chain(Fun<String, B> f, Fun<B, C> g) {
    return g.apply(f.apply("_73_74rDbbE"));
  }

  // Overloaded so that Test::parse is an inexact method reference.
  static Integer parse(String s) throws NumberFormatException {
    return Integer.valueOf(s);
  }

  static Integer parse(StringBuilder s) throws NumberFormatException {
    return Integer.valueOf(s.toString());
  }
}

interface ThrowingFun<I, O, E extends Exception> {
  O apply(I i) throws E;
}

interface Fun<I2, O2> {
  O2 apply(I2 i);
}
