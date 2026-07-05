// .result: COMPILE_PASS
import java.util.*;
import java.util.stream.*;

public class Test {
  void setScope(SubConstraint[] constraints) {
    setScope(Arrays.stream(constraints).map(Constraint::arguments).flatMap(Collection::stream));
  }

  void setScope(Stream<Var> scope) { }
}

class Var { }

class Constraint {
  public Set<Var> arguments() {
    return Collections.emptySet();
  }
}

class SubConstraint extends Constraint { }
