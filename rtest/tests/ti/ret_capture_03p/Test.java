// Test capture conversion of wildcard involving inference variable targeting a wildcard type
// involving a different inference variable.
// Test for capture bounds.
// .result: COMPILE_PASS
import java.util.ArrayList;
import java.util.List;

public class Test {
  static <Math> List<? extends Math> maths(Math seed) {
    return new ArrayList<>();
  }

  static <Matte> Matte translate(List<? extends Matte> list) {
    return list.get(0);
  }

  CharSequence c = translate(maths("algebra"));
}
