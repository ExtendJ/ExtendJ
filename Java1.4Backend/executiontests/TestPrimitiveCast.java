public class TestPrimitiveCast {
  static void test(double i) {
    System.out.println(i);
  }
  public static void main(String[] args) {
    /*
    int i = 1000;
    byte b = (byte)i;
    System.out.println(b);
    short s = (short)i;
    System.out.println(s);
    char c = (char)i;
    System.out.println(c);
    float f = (float)i;
    System.out.println(f);
    int j = (int)i;
    System.out.println(j);

    float f = 3;
*/
   /*
    float f = 1.2f;
    byte b = (byte)f;
    System.out.println(b);

    double d = 2.6;
    char c = (char)d;
    System.out.println(d);
    */

    /*
    long l = 3L;
    short s = l;
    System.out.println(s);

    int i = 3;
    int j = (int)i;*/
    short b = 3;
    test(b);
    float f = (int)2.1;
    System.out.println(f);
    System.out.println(f+b);
    double d = f + b;
    System.out.println(d);
    test(f+b);
    Object o = new TestPrimitiveCast();
    System.out.println("String: " + o + false + (byte)1 + (short)2 + '3' + 4 + 5L + 6.0f + 7.0D);
  }

  public String toString() {
    return "Hello";
  }
}
