public class TestInnerPrivate {
  private int f = 1;
  private static double d = 1.0;
  byte b = 1;
  char c = 65;
  long l = 3;
  private void a(long l, int i) {
    System.out.println("a" + l + i);
  }
  static private int b(short s) {
    return s;
  }

  private TestInnerPrivate() {
    System.out.println("A");
  }
  class Inner {
    Inner() {
      System.out.println(++f);
      ++f;
      System.out.println(f);
      f++;
      System.out.println(f);
      System.out.println(f++);
      System.out.println(f);
      System.out.println(++d);
      ++d;
      System.out.println(d);
      d++;
      System.out.println(d);
      System.out.println(d++);
      System.out.println(d);
      System.out.println(++b);
      ++b;
      System.out.println(b);
      b++;
      System.out.println(b);
      System.out.println(b++);
      System.out.println(b);
      System.out.println(++c);
      ++c;
      System.out.println(c);
      c++;
      System.out.println(c);
      System.out.println(c++);
      System.out.println(c);
      System.out.println(--l);
      --l;
      System.out.println(l);
      l--;
      System.out.println(l);
      System.out.println(l--);
      System.out.println(l);
      a(1,2);
      System.out.println(b((short)3));
      TestInnerPrivate p = new TestInnerPrivate();
    }
  }

  public static void main(String[] args) {
    new TestInnerPrivate().new Inner();
  }
}
