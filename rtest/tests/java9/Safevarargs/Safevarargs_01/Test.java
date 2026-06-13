// .result=COMPILE_PASS
public class Test {
     @SafeVarargs
    final <T> void foo(T... args) {
        for (T x : args) {
            // do stuff with x
        }
    }

    @SafeVarargs
    private <T> void bar(T... args) {
        for (T x : args) {
            // do stuff with x
        }
    }
}