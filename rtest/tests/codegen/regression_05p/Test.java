// Test regression in codegen causing a private constructor access to generate
// incorrect code in an anonymous class instance expression.
// .result=EXEC_PASS
public class Test {
  public static void main(String[] args) {
    if (!Value.namedValue("bar", 8888).string().equals("bar = 8888")) {
      throw new Error("expected \"bar = 8888\"");
    }
  }
}

abstract class Value {
  int v;

  private Value(int value) {
    v = value;
  }

  public abstract String string();

  public static Value namedValue(final String name, int value) {
    return new Value(value) {
      public String string() {
        return name + " = " + v;
      }
    };
  }
}
