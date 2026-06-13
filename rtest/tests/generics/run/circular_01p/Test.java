// Test a generic type with circular type argument dependencies.
public class Test {
  public static void main(String[] args) {
    Dual<IntContainer, StrContainer> con =
        Dual.<IntContainer, StrContainer>left(new IntContainer(123));
    System.out.println(con.a.getClass().getSimpleName() + ": " + con.a.val);
    System.out.println(con.b.getClass().getSimpleName() + ": " + con.b.val);
    con = Dual.<IntContainer, StrContainer>right(new StrContainer("102030"));
    System.out.println(con.a.getClass().getSimpleName() + ": " + con.a.val);
    System.out.println(con.b.getClass().getSimpleName() + ": " + con.b.val);
  }
}

interface Transformable<T> {
  T transform();
}

class Dual<A extends Transformable<B>, B extends Transformable<A>> {
  public final A a;
  public final B b;

  private Dual(A a, B b) {
    this.a = a;
    this.b = b;
  }

  public static <U extends Transformable<V>, V extends Transformable<U>> Dual<U, V> left(U u) {
    return new Dual<U, V>(u, u.transform());
  }

  public static <U extends Transformable<V>, V extends Transformable<U>> Dual<U, V> right(V v) {
    return new Dual<U, V>(v.transform(), v);
  }
}

class IntContainer implements Transformable<StrContainer> {
  public final Integer val;

  public IntContainer(Integer val) {
    this.val = val;
  }

  public StrContainer transform() {
    return new StrContainer(val.toString());
  }
}

class StrContainer implements Transformable<IntContainer> {
  public final String val;

  public StrContainer(String val) {
    this.val = val;
  }

  public IntContainer transform() {
    return new IntContainer(Integer.parseInt(val));
  }
}
