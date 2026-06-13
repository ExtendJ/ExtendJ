// Tests semantic error when inheriting return type incompatible methods
// This test is very similar to type/override_04f. The difference is that
// the inheriting class does not also declare the conflicting method.
// https://bitbucket.org/jastadd/jastaddj/issue/87/inherited-methods-with-incompatible-return
// .result=COMPILE_FAIL
interface I {
  int f();
}

class C {
  public String f() {
    return "x";
  }
}

abstract class Test extends C implements I {
}
