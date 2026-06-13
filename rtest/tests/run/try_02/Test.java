public class Test {
  public static void main(String[] args) {
    try {
      f();
    } catch (Error t) {
      System.out.println(t.getMessage());
    } catch (Exception t) {
      System.out.println("fail");
    }
  }

  static void f() throws Exception {
    throw new Error("x marks the spot");
  }
}
