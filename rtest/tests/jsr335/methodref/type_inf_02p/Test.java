// This test exposes a circularity in the ExtendJ type inference
// for generic method references.
// See issue 180.
// .result=COMPILE_PASS
import java.util.List;

public class Test {
  <B> B convert() {
    return null;
  }

  void m2(List<Test> list) {
    list.stream().map(Test::convert);
  }
}
