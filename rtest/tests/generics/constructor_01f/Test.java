// Test conflicting constructors for generic class due to type parameterization
// http://svn.cs.lth.se/trac/jastadd-trac/ticket/59
// .result=COMPILE_FAIL

public class Test {
    void m() {
        C<F> c = new C<F>();
        c.new D(new F());
    }
}

class C<X> {
    class D {
        D(X x) { }
        D(F f) { }
    }
}

class F { }
