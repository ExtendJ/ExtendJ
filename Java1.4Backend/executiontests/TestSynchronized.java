public class TestSynchronized {
  public static void main(String[] args) {
    for(int i = 0; i < 10; i++) {
      synchronized(args) {
        System.out.println("First");
        break;
      }
    }
    try {
      synchronized(args) {
        throw new Error();
      }
    }
    catch(Error e) {
      System.out.println("Second");
    }
    synchronized(args) {
      System.out.println("Third");
    }
    synchronized(args) {
      System.out.println("Fourth");
      return;
    }
  }
}
