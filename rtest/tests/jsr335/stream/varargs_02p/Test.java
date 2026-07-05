import java.util.*;
import java.util.stream.*;

public class Test {
  public static void main(String[] args) {
    String[] vars = { "July", "5", "2026" };
    printVars(Arrays.stream(vars).map(Var::new));
  }

  static void printVars(Var... vars) {
    for (Var v : vars) {
      System.out.println(v);
    }
  }

  static void printVars(Stream<Var> vars) {
    printVars(vars.toArray(Var[]::new));
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
