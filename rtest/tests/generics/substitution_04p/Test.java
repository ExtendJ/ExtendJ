// Test type substitution in inherited enum methods.
// .result=COMPILE_PASS
public class Test {
  void test(E e) {
    Class<E> decl = e.getDeclaringClass();
    E.valueOf(decl, "C4");
  }
}

enum E {
  C1, C2, C3, C4;
}
