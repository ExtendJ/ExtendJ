// Test chained inference variable dependencies.
// .result: COMPILE_FAIL
import java.io.IOException;

public class Test {
  {
    // The checked exception constraint of the second lambda can only be reduced
    // once the first constraint gives the lambda a proper parameter type.
    // Reducing it makes IOException a bound of Prose, so the exception is
    // reported at the invocation rather than in the lambda body.
    run(() -> "myFKmJ7GYfw", str -> { throw new IOException(str); });
  }

  static <Val, Prose extends Exception> void run(Src<Val> src, Goblin<Val, Prose> blk) throws Prose {
    blk.accept(src.get());
  }
}

interface Src<Sv> {
  Sv get();
}

interface Goblin<Bv, Be extends Exception> {
  void accept(Bv v) throws Be;
}
