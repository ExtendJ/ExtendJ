public class Test {
  public static void main(String[] args) {
    boolean x = args.length == 1;
    boolean y = args.length > 100;
    if (x || y) {
      System.out.println("fail");
    }
  }
}
