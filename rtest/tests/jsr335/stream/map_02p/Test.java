import java.util.*;
import java.util.stream.*;

public class Test {
  public static void main(String[] args) {
    String[] vars = { "sAx", "XUY", "uL", "80s\n" };
    pr(Arrays.stream(vars).map(Var::new));
  }

  static void pr(Var... vars) {
    for (Var v : vars) {
      System.out.print(v);
    }
  }

  static void pr(Stream<Var> vars) {
    pr(vars.toArray(Var[]::new));
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
