// Tests NPE thrown during ParMethodDecl type argument substitution
// http://svn.cs.lth.se/trac/jastadd-trac/ticket/86
// .result=COMPILE_PASS
public class Test<P> {
    {
        m(null, null);
    }

    public <T> void m(T t, P p) {
    }
}
