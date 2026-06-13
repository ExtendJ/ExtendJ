// Tests accessing a protected member of an enclosing class inherited from
// common superclass declared in another package.
// http://svn.cs.lth.se/trac/jastadd-trac/ticket/52
// .result=COMPILE_PASS
import p1.C;
class Test extends C {
  class Inner extends C {
    {
      Test.this.m();
    }
  }
}
