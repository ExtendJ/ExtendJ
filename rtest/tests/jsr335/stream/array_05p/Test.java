// .result: COMPILE_PASS
import java.util.*;
import java.util.stream.*;

public class Test {
  void july5() {
    Store store = new Store();
    Var[] items = new Var[0];
    go(store, Arrays.stream(items).toArray(Var[]::new));
    // <A>toArray(IntFunction<A[]>)
  }

  int go(Store store, Var[]... variables) {
    return 0;
  }
}

class Store {}
class Var {}
