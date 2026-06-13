// A parameterized type is a subtype of java.lang.Object.
// .result: COMPILE_PASS
public class Test {
  Object pass(Container<Status> con) {
    return con;
  }
}

interface Container<T> { }
class Status { }
