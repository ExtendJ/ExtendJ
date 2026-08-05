// Test for stack map frame generation bug in ExtendJ.
// See issue 288.
public class Test implements AutoCloseable {
  public static void main(String[] args) {
    try (Test p0 = new Test();
        Test parser = new Test()) {
      System.out.println("hi mom");
      return;
    } catch (Throwable e) {
    }
  }

  public void close() { }
}
