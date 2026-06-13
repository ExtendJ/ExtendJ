// Test a simple method reference type inference case.
// This is a simpler version of jsr335/methodref/type_inf_02p
// .result=COMPILE_PASS
import java.util.List;
import java.util.stream.*;

public class Test {
  void m2(Stream<Test> strm) {
    strm.map(Test::convert);
  }

  <B> B convert() {
    return null;
  }
}
