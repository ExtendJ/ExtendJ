// This tests that equality constraints like T=T are correctly handled.
// https://bitbucket.org/extendj/extendj/issues/214/type-inference-regression
// .result: COMPILE_PASS
public abstract class Test {
  @SuppressWarnings("unchecked")
  <T extends Note> T get(Class<T> note) {
    // The initial constraints for get(note) are:
    // * T <: Note
    // * T = T
    //
    // The equality constraint may seem redundant, but if it is
    // removed, then T is inferred to have type Note, and then
    // the method call is not applicable.
    Note a = get(note);
    return (T) a;
  }
}

interface Note { }
