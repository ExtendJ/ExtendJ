// .result: COMPILE_PASS
import java.util.*;
import java.util.function.*;

public abstract class Test<T> {
  abstract <R> Pipe<R> toPipe(Collection<R> c);

  void incorporate(Pipe<Set<Var>> sets) {
    setScope(sets.flatMap(this::toPipe));
  }

  void setScope(Pipe<Var> scope) { }
  void setScope(SubConstraint[] scope) { }
}

interface Pipe<T> {
  <R> Pipe<R> flatMap(Function<? super T, ? extends Pipe<? extends R>> fn);
}

class Var { }

class Constraint {

  public Set<Var> arguments() {
    return Collections.emptySet();
  }

}

class SubConstraint extends Constraint {
}
