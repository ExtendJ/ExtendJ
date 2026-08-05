// Test that a boxing operation is generated when assigning a boolean to Boolean.
// See issue 239.
public class Test {
  public static void main(String[] args) {
    boolean b = true;
    Boolean boxed = b;
    System.out.println(boxed);
  }
}
