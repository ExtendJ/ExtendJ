// .result=COMPILE_PASS
public class Test {
  @SafeVarargs
  public static <T> void perform(Subject<? extends T>... subjects) {
    Helper.perform(subjects); // Calls the first helper method.
  }
}

class Helper {
  // First helper method:
  @SafeVarargs
  public static <T> void perform(Subject<? extends T>... subjects) {
  }

  // Second helper method:
  @SafeVarargs
  public static <T> void perform(T... subjects) {
  }
}

class Subject<T> {
}
