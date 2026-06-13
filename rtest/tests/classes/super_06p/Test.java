// Tests a use of inner class instantiation via anonymous class instance
// in separate class.
// https://bitbucket.org/extendj/extendj/issues/187/too-conservative-anonymous-class-type
// .result=COMPILE_PASS

class Outer {
  class IC {
  }
}

public class Test {
  public static void main(String[] args) {
    try {
      // This does not generate a static error, but
      // causes an illegal access error during runtime.
      new Outer().new IC() { };
    } catch (IllegalAccessError e) {
      return;
    }
    throw new Error("Expected IllegalAccessError");
  }
}
