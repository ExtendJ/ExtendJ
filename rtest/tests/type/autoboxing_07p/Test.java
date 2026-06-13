// Test that a boxing operation is generated when assigning a boolean to Boolean.
// https://bitbucket.org/extendj/extendj/issues/239/missing-boxing-conversion-for-boolean
public class Test {
  public static void main(String[] args) {
    boolean b = true;
    Boolean boxed = b;
    System.out.println(boxed);
  }
}
