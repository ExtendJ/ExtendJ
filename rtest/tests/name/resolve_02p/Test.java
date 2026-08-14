// Test name resolution for a field access chain on a method invocation used as
// the right hand side of an assignment.
// See issue 346.
// .result: COMPILE_PASS
public class Test {
  String name;

  void test() {
    String local;
    local = get().thing.label;        // Error: classified as a package name.
    name = get().thing.label;         // Error: classified as a package name.
    local = get().thing.label.trim(); // A method invocation ends the chain.
    String init = get().thing.label;  // A declarator, not an assignment.
    Other other = get();
    local = other.thing.label;        // A variable, not a method invocation.
  }

  Other get() {
    return null;
  }
}

class Other {
  Thing thing;
}

class Thing {
  String label;
}
