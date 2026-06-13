// Test for stack map frame generation bug in ExtendJ.
// https://bitbucket.org/extendj/extendj/issues/288/stack-map-error-for-twr-statement-with
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
