public class TestPrePostInc {
  static int j = 0;
  public static void main(String[] args) {
    int i = 0;
    i++;
    System.out.println(i++);
    System.out.println(i);
    j++;
    System.out.println(j);
    System.out.println(++j);
    System.out.println(j++);
    System.out.println(j);
  } 
}
