// .result: COMPILE_PASS
import java.util.*;
import java.util.stream.*;

public class Test {
  List<Pair> run(
      Stream<Pair<Library, Status>> stream,
      Library lib,
      Status newStatus) {
    return stream.map(
        item -> {
          if (item.l != lib) {
            return item;
          } else {
            return new Pair<>(item.l, newStatus);
          }
        }).collect(Collectors.toList());
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
