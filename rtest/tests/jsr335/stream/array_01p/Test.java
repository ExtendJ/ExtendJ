// Test using arrays and streams.
import java.util.*;
import java.util.stream.*;

public class Test {
  public static void main(String[] args) {
    String[] names = { "Ex", "Cl", "In" };
    Var[] vars = Arrays.stream(names).map(Var::new).toArray(Var[]::new);
    Arrays.stream(vars).forEach(System.out::println);
  }
}

class Var {
  public final String name;
  public Var(String name) {
    this.name = name;
  }
  @Override public String toString() {
    return name;
  }
}
