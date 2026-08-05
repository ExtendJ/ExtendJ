// Test code generation error in ExtendJ: non existent accessor
// method call is generated for field access in generic type which
// does not require accessor.
// See issue 293.
@SuppressWarnings("unchecked")
public class Test<E> {
  private Object value = "hi mom";

  public static void main(String[] args) {
    System.out.println(toString(new Test()));
  }

  static String toString(Test an) {
    return an.value.toString();
  }
}
