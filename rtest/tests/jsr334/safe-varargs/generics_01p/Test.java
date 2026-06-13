// Method type inference test.
// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface Thing { }

public abstract class Test {
  @SuppressWarnings("unchecked")
  abstract <T> List<T> asList(T... a);

  abstract Thing build(final String name, final Object value);

  List<Thing> buildList(int[] ids) {
    List<Thing> list = new ArrayList<Thing>();
    for (final int id : ids) {
      list.add(build(asList(ids).toString(), id));
    }
    return list;
  }
}
