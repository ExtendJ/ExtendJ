// https://bitbucket.org/extendj/extendj/issues/244/incorrect-stackmap-frame-for-exception
public class Test {
  public static void main(String[] args) {
    Object lock = new Object();

    {
      int z = 1;
    }
    try {
      {
        int w = 2;
        if (args.length > 0) {
          w += 1;
        }
      }

      synchronized (lock) {
        return;
      }
    } catch (Exception e) {
      System.out.println("Exception:" + e.getMessage());
    }
  }
}

