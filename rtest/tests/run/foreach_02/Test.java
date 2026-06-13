// Regression test for stack map frame generation issue.
// https://bitbucket.org/extendj/extendj/issues/302/stack-map-error-when-iterating-over-int
public class Test {
  public static void main(String[] args) {
    int[] x = { 12321 };
    long last = 0;
    for (long i : x) {
      int pow = (int)i;
      System.out.println(i);
    }
  }
}
