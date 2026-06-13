// Test subtyping rules for parameterized types.
// .result: COMPILE_FAIL
public class Test {
  Container<Status> run(Container<SubStatus> con) {
    return con;
  }
}

interface Container<T> { }
class Status { }
class SubStatus extends Status { }
