// Test that ambiguous method access is reported.
// .result=COMPILE_FAIL
class ParType<T> {
    public void m(T x) { }
    public void m(Integer x) { }
}

public class Test {
    public void f(ParType<Integer> x) {
        x.m(123);// ambiguous method access!
    }
}
