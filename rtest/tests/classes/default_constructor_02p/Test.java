// The default constructor may throw an unchecked exception.
// .result=COMPILE_PASS
class Super {
  Super() throws Error {
  }
}

public class Test extends Super {
  // OK: default constructor throws an unchecked exception via super() call.
}
