public class TestInnerA {
  public TestInnerA() {
    System.out.println("A");
  }
  void a() {
    System.out.println("a");
  }
  class TestInnerB {
    TestInnerC c;
    TestInnerB() {
      System.out.println("B");
      a();
    }  
    void b() {
      System.out.println("b");
    }
    void createC() {
      c = new TestInnerC();
    }
      
    class TestInnerC {
      TestInnerC() {
        System.out.println("C");
      }
      void c() {
        TestInnerA.this.a();
        TestInnerB.this.b();
        System.out.println("c");
      }
    }
  }
  public static void main(String[] args) {
    TestInnerA a = new TestInnerA();
    a.a();
    TestInnerB b = a.new TestInnerB();
    b.b();
    b.createC();
    b.c.c();
    
  }
}
