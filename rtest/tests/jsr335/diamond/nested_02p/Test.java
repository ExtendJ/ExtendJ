// A simplified version of nested_01p which does not use the Java standard library.
// See issue 266.
// .result: COMPILE_PASS
public class Test {
  A<String> a = new A<>(new B<>());
}

class A<T> {
  A(B<T> b) { }
}
class B<T> { }
