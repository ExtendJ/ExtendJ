public class Test {
  public static void main(String[] args) {
    Function<String, Helper> fun = message -> new SubHelper(message);
    System.out.println(fun.apply("tPan-yeptRw"));
  }
}

interface Function<I, O> {
  O apply(I i);
}

class Helper { }

class SubHelper extends Helper {
  public final String msg;
  public SubHelper(String msg) {
    this.msg = msg;
  }
  @Override public String toString() {
    return msg;
  }
}
