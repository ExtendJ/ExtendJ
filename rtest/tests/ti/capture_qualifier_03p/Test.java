// The capture variable of a lower bounded wildcard has the bound of the
// wildcard as its lower bound, so a subtype of the bound can
// be assigned to a member typed by the capture variable.
// .result: COMPILE_PASS
public class Test {
  void m(Box<? super B> b) {
    b.value = new C();
    b.set(new C());
  }
}

class A { }
class B extends A { }
class C extends B { }

class Box<T> {
  T value;

  void set(T t) { }
}
