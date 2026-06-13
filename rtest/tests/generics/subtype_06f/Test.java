// Test subtyping rules for parameterized types.
// .result: COMPILE_FAIL
public class Test {
  Container<Container> run(Container<Container<Status>> con) {
    return con;
  }
}

interface Container<T> { }
class Status { }
