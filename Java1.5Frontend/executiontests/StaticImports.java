import static java.lang.Math.*;
import static java.lang.Math.sqrt;
import static java.lang.Math.min;

public class StaticImports {
  public static void main(String[] args) {
    System.out.println(PI);
    System.out.println(sqrt(PI));
    System.out.println(abs(-5));
    System.out.println(java.lang.Math.max(1, min(3,2)));
  }
}
