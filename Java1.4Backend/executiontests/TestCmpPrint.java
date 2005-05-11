public class TestCmpPrint {
  public static void main(String[] args) {
    int i=3,j=7;
    boolean b = i<j;
    System.out.println(b);
    Object o = null;
    b = i<=j; System.out.println(b);
    b = i==j; System.out.println(b);
    b = i!=j; System.out.println(b);
    b = i>=j; System.out.println(b);
    b = i<j;  System.out.println(b);
    b = o == null; System.out.println(b);
    b = o != null; System.out.println(b);
    b = o == o; System.out.println(b);
    b = o != o; System.out.println(b);
    b = 5 < Double.NaN; System.out.println(b);
    b = 5 <= Double.NaN; System.out.println(b);
    b = 5 < 7; System.out.println(b);
    
    b = Double.NaN < Double.NaN; System.out.println(b);
    b = Double.NaN < 100.0; System.out.println(b);
    b = 5 < 100.0; System.out.println(b);
    b = 100 < 100.0; System.out.println(b);
    b = Double.NaN <= 100.0; System.out.println(b);
    b = 100 <= Double.NaN; System.out.println(b);
    b = 5 <= 100.0; System.out.println(b);
    b = 100 <= 100.0; System.out.println(b);
    b = Double.NaN >= 100.0; System.out.println(b);
    b = 5 >= 100.0; System.out.println(b);
    b = 100 >= 100.0; System.out.println(b);
    b = Double.NaN > 100.0; System.out.println(b);
    b = 5 > 100.0; System.out.println(b);
    b = 100 > 100.0; System.out.println(b);
  }
}
