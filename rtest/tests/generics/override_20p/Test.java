// Test that it is possible to override a generic method.
// .result=COMPILE_PASS
public class Test extends A {
  <V> C<V> build() {
    return new C<V>();
  }
}

class C<T> { }

class A {
  <U> C<U> build() {
    return new C<U>();
  }
}
