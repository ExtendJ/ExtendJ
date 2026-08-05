// This tests that boxing conversions are generated when passing a generic method argument.
// See issue 232.
// This test used to fail when passing a boolean to moo().
public class Test {
  public static void main(String[] args) {
    moo(0);
    moo(1);
    moo(true);
    moo(false);
    moo(true);
    moo("ok");
  }

  public static <T> void moo(T moo) {
    if (moo.equals(Boolean.TRUE)) {
      System.out.println("moo");
    }
  }

}
