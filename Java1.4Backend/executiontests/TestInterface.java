class TestInterface implements Interface2, Interface1 {
  public static void main(String[] args) {
    TestInterface i = new TestInterface();
    System.out.println(i instanceof Interface1);
    System.out.println(i instanceof Interface2);
    System.out.println(i instanceof Interface3);
  }
}

interface Interface1 {}
interface Interface2 {}
interface Interface3 {}
