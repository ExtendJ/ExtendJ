// .result=EXEC_PASS
interface UpperCase {
  String upperCase(String s);
}
public class Test {
  public static void main (String[] args) {
    UpperCase upper = s -> s.toUpperCase();
    System.out.println(upper.upperCase("July 9"));
  }
}
