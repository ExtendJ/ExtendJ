public class Test {
  public static void main(String[] args) throws Exception {
    final String lock = "xyz";
    final Counter counter = new Counter();
    Thread[] threads = new Thread[3];
    for (int i = 0; i < threads.length; ++i) {
      threads[i] = new Thread() {
        public void run() {
          for (int j = 0; j < 70; ++j) {
            synchronized (lock) {
              counter.value += 1;
            }
          }
        }
      };
    }
    for (int i = 0; i < threads.length; ++i) {
      threads[i].start();
    }
    for (int i = 0; i < threads.length; ++i) {
      threads[i].join();
    }
    synchronized (lock) {
      System.out.println(counter.value);
    }
  }
}

class Counter {
  public int value;
}
