public class Test {
  public static void main(String[] args) {
    Function<String, Helper> fun = message -> new Helper() {
      @Override public String toString() {
        return message;
      }
    };
    System.out.println(fun.apply("ql-s-bvikaM"));
  }
}

interface Function<I, O> {
  O apply(I i);
}

class Helper { }
