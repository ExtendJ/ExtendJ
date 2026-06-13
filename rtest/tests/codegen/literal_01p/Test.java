// Test that the correct constant values are generated in bytecode.
// https://bitbucket.org/extendj/extendj/issues/223/java-7-floating-point-literals-drop-their
// .result: EXEC_PASS
public class Test {
  public static void main(String[] args) {
    System.out.println("Float:");
    System.out.println(Float.MIN_VALUE);
    System.out.println(Float.MAX_VALUE);
    System.out.println(Float.NEGATIVE_INFINITY);
    System.out.println(Float.POSITIVE_INFINITY);
    System.out.println(Float.NaN);
    System.out.println();

    System.out.println("Double:");
    System.out.println(Double.MIN_VALUE);
    System.out.println(Double.MAX_VALUE);
    System.out.println(Double.NEGATIVE_INFINITY);
    System.out.println(Double.POSITIVE_INFINITY);
    System.out.println(Double.NaN);
    System.out.println();

    System.out.println("Integer:");
    System.out.println(Integer.MIN_VALUE);
    System.out.println(Integer.MAX_VALUE);
    System.out.println();

    System.out.println("Long:");
    System.out.println(Long.MIN_VALUE);
    System.out.println(Long.MAX_VALUE);
  }
}
