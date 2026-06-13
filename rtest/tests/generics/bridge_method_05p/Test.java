// Bridge methods are generated when, due to type erasure, a method in a
// subtype that should override a method in a supertype or interface no longer
// has the same signature as the method it overrides.
// This test checks that multiple bridge methods are generated when
// there are multiple overridden methods with different signature after
// type erasure.
// .result=EXEC_PASS
public class Test {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    C1<Integer> c3 = new C3<Integer>();
    C1<Integer> r3 = c3.build();
    if (!(r3 instanceof C3)) {
      throw new Error("Wrong type: " + r3.getClass().getName());
    }
    C1<Integer> c2 = new C2<Integer>();
    C1<Integer> r2 = c2.build();
    if (!(r3 instanceof C2)) {
      throw new Error("Wrong type: " + r2.getClass().getName());
    }
  }
}

interface I<T> {
  I<T> build();
}

class C1<T> {
  public C1<T> build() {
    return new C1<T>();
  }
}

class C2<T> extends C1<T> {
  // Bridge method: build()LC1;
  public C2<T> build() {
    return new C2<T>();
  }
}

class C3<T> extends C2<T> implements I<T> {
  // Bridge method1: build()LC1;
  // Bridge method2: build()LC2;
  public C3<T> build() {
    return new C3<T>();
  }
}
