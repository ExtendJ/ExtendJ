import java.util.*;
import java.util.stream.*;

public class Test {
  public static void main(String[] args) {
    String[] vars = { "banan", "melon", "kiwi", "citron" };
    printVars(Arrays.stream(vars).map(Var::new));
  }

  static void printVars(Stream<Var> vars) {
    vars.forEach(System.out::println);
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
