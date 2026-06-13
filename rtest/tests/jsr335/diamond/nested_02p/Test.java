// A simplified version of nested_01p which does not use the Java standard library.
// https://bitbucket.org/extendj/extendj/issues/266/stack-overflow-caused-by-nested-diamond
// .result: COMPILE_PASS
public class Test {
  A<String> a = new A<>(new B<>());
}

class A<T> {
  A(B<T> b) { }
}
class B<T> { }
