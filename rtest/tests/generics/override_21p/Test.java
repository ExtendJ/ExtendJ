// Test that an overriding generic method can declare its own type
// variable in its throws clause.
// .result=COMPILE_PASS
public class Test implements Api {
  public <Ex2 extends Exception> void run(Box<Ex2> box) throws Ex2 { }
}

interface Api {
  <Ex1 extends Exception> void run(Box<Ex1> box) throws Ex1;
}

class Box<T> { }
