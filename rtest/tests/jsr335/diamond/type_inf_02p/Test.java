// .result: COMPILE_PASS
import java.util.*;

public class Test {
  void pass() {
    Con<IntVar> set = new SimpleCon<>(new Domain<>());
  }
}

class Var { }
class IntVar extends Var { }

interface Con<T extends Var> { }

class SimpleCon<T extends Var> implements Con<T> {
  public SimpleCon(Domain<T> d) {}
}

class Domain<T extends Var> {
  public Domain() { }
}

