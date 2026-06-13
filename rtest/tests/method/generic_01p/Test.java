// Test most specific method evaluation.
// .result=COMPILE_PASS
public class Test {
  public static <T> void perform(Subject<? extends T> subject) {
    Helper.perform(subject); // Calls the first helper method.
  }
}

class Helper {
  // First helper method:
  public static <T> void perform(Subject<? extends T> subject) {
  }

  // Second helper method:
  public static <T> void perform(T subject) {
  }
}

class Subject<T> {
}
