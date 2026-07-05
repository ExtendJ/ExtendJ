// .result: COMPILE_PASS
import java.util.*;
import java.util.stream.*;

public class Test {
  List<Pair> run(Stream<Pair<Library, Status>> stream) {
    return stream.map(item -> item).collect(Collectors.toList());
  }
}

class Pair<L, R> {
  public final L l;
  public final R r;

  public Pair(L l, R r) {
    this.l = l;
    this.r = r;
  }
}

class Library { }
class Status { }
