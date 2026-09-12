// Test inference targeting tvar from enclosing method.
// .result: COMPILE_PASS
public abstract class Test {
  abstract <U> U masonry(U u);

  <Z extends Sten<Z>> Z[] stenkross(Z[] sten) {
    // Target type Z[] is a proper type here.
    return masonry(sten);
  }
}

interface Sten<T> { }
