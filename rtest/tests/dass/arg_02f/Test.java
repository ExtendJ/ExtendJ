// Test for definite unassignedness bug.
// https://bitbucket.org/jastadd/jastaddj/issue/52/methodaccess-lacks-definite-un
// .result=COMPILE_FAIL
public class Test {
  public static void foo(int a) {
  }

  public static void main(String[] args) {
    final int f;
    boolean b = args[0].length() == 1;
    foo(b ? 3 : (f = 5));
    f = 4; // Error: f not definitely unassigned here.
    System.out.println(""+f);
  }
}
