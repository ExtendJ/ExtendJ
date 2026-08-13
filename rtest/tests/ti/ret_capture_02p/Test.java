// Test capture conversion of wildcard involving inference variable targeting a wildcard type.
// .result: COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

public class Test {
  static <Model> List<? extends Model> make(Model base) {
    return new ArrayList<>();
  }

  List<? extends CharSequence> l = make("Mark II");
}
