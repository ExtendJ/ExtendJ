// It is an error if the default constructor throws a checked exception.
// .result=COMPILE_FAIL
class Super {
  Super() throws Exception {
  }
}

public class Test extends Super {
  // Error: default constructor throws an exception via super() call.
}
