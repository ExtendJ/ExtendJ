// Test an overriding case that produced errors.
// See https://bitbucket.org/extendj/extendj/issues/165/clone-overriding-error
// .result=COMPILE_PASS
interface Interface {
  public Interface clone();
}

abstract class Base implements Interface {
  @Override
  public Interface clone() {
    return null;
  }
}

class Test extends Base implements Interface {
}
