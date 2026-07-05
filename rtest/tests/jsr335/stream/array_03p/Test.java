// .result: COMPILE_PASS
import java.util.*;
import java.util.stream.*;

public class Test {
  void setScope(Var[]... variables) {
    //  Stream<Var[]>  ->  [Stream<Stream<Var>>]  ->  [ Stream<Var> ]
    setScope(Arrays.stream(variables).map(Arrays::stream).flatMap(i -> i));
  }

  void setScope(Stream<Var> scope) { }
}

class Var {}
