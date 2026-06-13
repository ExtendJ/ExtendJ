// Test try-statement control flow.
public class Test {
  public static void main(String[] args) throws Exception {
    System.out.println(new Test().run());
  }

  public int run() throws Exception {
    int result = -1;
    try {
      try {
        return result;
      } finally {
        result = 0;
      }
    } catch(Exception ex) {
      return result;
    }
  }
}
