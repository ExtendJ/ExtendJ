public class Test {
  public static void main (String[] args) {
    Add add  = (var a, var b)->a+b;
    System.out.println(add.sum(100, 20));
  }
}
interface Add {
  int sum(int x, int y);
}
