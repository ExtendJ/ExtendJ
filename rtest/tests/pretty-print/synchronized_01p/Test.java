// Test that synchronized statements are pretty-printed correctly.
// .result=COMPILE_OUT
// .options=XprettyPrint
public class Test {
  void f() {
    synchronized (new Object()) {
      System.out.println("not critical");
    }
  }
}
