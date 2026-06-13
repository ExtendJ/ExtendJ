// If the superclass does not have a constructor taking no arguments, then a
// default constructor can not be generated.
// .result=COMPILE_FAIL

class Super {
  Super(int x) {
  }
}

public class Test extends Super {
  // Error: superclass does not have a constructor taking no arguments.
}
