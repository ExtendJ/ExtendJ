public class TestBreakFinally {
  public static void main(String[] args) {
    try {
      System.out.println("First Try");
      try {
        System.out.println("Try begin");
        return;
        //break;
      }
      /*catch(Error e) {
        System.out.println("Error caught");
      }*/
      finally {
        System.out.println("Finally");
      }
    }
    catch(Error e) {
      System.out.println("Error caught outer");
    }
    finally {
      System.out.println("Finally outer");
    }
      
  }
}
