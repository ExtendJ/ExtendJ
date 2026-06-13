// Cyclic type variable bounds are not allowed.
// .result=COMPILE_FAIL
public class Test<S extends S> {
}
