// Try calling a method on a typecasted expression.
public class Test {
  public static void main(String[] args) {
    bamboozle(new Some("thing was done"));
  }

  static void bamboozle(Any any) {
    ((Some) any).doThing();
  }
}

class Any {
}

class Some extends Any {
  private final String message;

  public Some(String message) {
    this.message = message;
  }

  public void doThing() {
    System.out.println(message);
  }
}
