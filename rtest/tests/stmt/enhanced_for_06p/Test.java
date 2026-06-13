// Test that ExtendJ can generate code for an enhanced for statement iterating over
// a type implementing a java.lang.Iterable with no type arguments.
// .result=COMPILE_PASS
public class Test {
  public static class MyList implements Iterable {
    public java.util.Iterator iterator() {
      return null;
    }
  }
  public static void main(MyList list) {
    for (Object item : list) {
      System.err.println(item);
    }
  }
}
