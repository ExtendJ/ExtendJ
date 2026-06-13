// Test accessing a protected member of an enclosing type via a method qualifier.
// https://bitbucket.org/extendj/extendj/issues/299/ambigous-name-resolution-incorrectly
// .result: COMPILE_PASS
import p1.C;
class Test extends C {
  class Inner extends C {
    class Inner2 extends C {
      Test outer() {
        return Test.this;
      }

      int get() {
        return outer().m;
      }

      int getStatic() {
        return outer().static_field;
      }
    }
  }
}
