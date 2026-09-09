// Test a captured type argument against an inferred intersection type.
// .result=COMPILE_PASS
interface Color {}
interface Hue {}
class Blue implements Color, Hue {}
class Green implements Color, Hue {}
class Breen<E extends Color & Hue> {
  E get() { return null; }
}
public abstract class Test {
  abstract <X> X glb(X _1, X _2, X _3);

  void intersect(Breen<?> breen) {
    // Caputred E must be subtype of X which is inferred as Color & Hue.
    glb(new Blue(), new Green(), breen.get());
  }
}
