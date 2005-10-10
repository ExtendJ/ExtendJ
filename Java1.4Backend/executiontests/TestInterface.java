public class TestInterface implements Interface2, Interface1 {
  public static void main(String[] args) {
    TestInterface i = new TestInterface();
    System.out.println(i instanceof Interface1);
    System.out.println(i instanceof Interface2);
    System.out.println(i instanceof Interface3);
    System.out.println(i instanceof Interface5);
  }
}

interface Interface1 extends Interface4 {}
interface Interface2 extends Interface4, Interface5 {}
interface Interface3 {}
interface Interface4 {}
interface Interface5 {}
