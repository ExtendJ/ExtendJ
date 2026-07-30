// The type of a field access is the type of the field in the capture converted
// type of the qualifier, so the field has the capture variable of the wildcard
// as its type and no value can be assigned to it.
// .result: COMPILE_FAIL
public class Test {
  void m(Box<?> b, Object x) {
    b.value = x;
  }
}

class Box<T> {
  T value;
}
