// Tests that uninitialized locals are handled in stackmap frames.
// https://bitbucket.org/extendj/extendj/issues/248/incorrect-stackmap-frames-error-no
public class Test {
  public static void main(String[] args) {
    boolean b = args.length > 0;
    Binary t = new Binary(!b);
  }

  static class Binary {
    public Binary(boolean value) {
      System.out.println(value);
    }
  }

}
