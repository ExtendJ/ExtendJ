public class TestCastArray {
  public static void main(String[] args) {
    String[] string = { "hello" };
    Object[] object = string;
    String[][] strings = new String[1][1];
    strings[0] = (String[])object; 
    System.out.println(strings[0][0]);
    if(new Object[] { object } instanceof String[])
      System.out.println("Instance");
    else
      System.out.println("No Instance");
    try {
      strings[0] = (String[])new Object[] { object }; 
    } catch (ClassCastException e) {
      System.out.println("ClassCastException");
    }
  }
}
