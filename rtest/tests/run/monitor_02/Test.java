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
              counter.inc();
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
    System.out.println(counter.get());
  }
}

class Counter {
  private int value;

  public synchronized void inc() {
    value += 1;
  }

  public synchronized int get() {
    return value;
  }
}
