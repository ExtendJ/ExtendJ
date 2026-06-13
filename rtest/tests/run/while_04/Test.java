// Test that codegeneration creates valid bytecode when using
// a preincrement/predecrement expression on a field that is accessed
// via accessor methods.
// https://bitbucket.org/extendj/extendj/issues/277/broken-bytecode-when-using-preincrement
public class Test {
  public static void main(String[] args) {
    Loop loop = new Test().new Loop();
    System.out.println(loop.run(100));
  }

  private int count = 1;

  class Loop {
    public int run(int num) {
      int sum = 0;
      while (count < num) {
        sum += count * count;
        --count;
        ++count;
        ++count;
      }
      return sum;
    }
  }
}
