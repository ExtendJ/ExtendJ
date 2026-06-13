// An anonymous class can be created in the same enclosing class as its
// superclass, even when nested inside another anonymous class.
// .result=COMPILE_PASS
import java.util.Iterator;

public class Test {
  public class Inner {
    boolean g(int x) {
      return x > 3;
    }
  }

  Iterator<Inner> build() {
    return new Iterator<Inner>() {
      public void remove() {
        throw new Error();
      }

      public boolean hasNext() {
        return true;
      }

      public Inner next() {
        // This anonymous class is an inner class of the enclosing class of
        // Test.Inner so we don't need to provide an enclosing instance.
        return new Inner() {
          boolean g(int x) {
            return x < 8;
          }
        };
      }
    };
  }
}
