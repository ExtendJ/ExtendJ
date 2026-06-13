// Test stack map frame generation for conditional returning
// a generic array type.
// https://bitbucket.org/extendj/extendj/issues/233/bad-stack-map-frame-when-using-conditional
public class Test {
  public static void main(String[] args) {
    System.out.println(foo(false).length);
    System.out.println(foo(true).length);
  }

  static Class<?>[] foo(boolean b) {
    return b ? new Class<?>[10] : new Class[20];
  }
}
