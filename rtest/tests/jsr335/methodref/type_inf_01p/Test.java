// Calculating the type of a method reference that is not a generic
// method should not trigger type inference.
// See https://bitbucket.org/extendj/extendj/issues/180/method-reference-stack-overflow-error
// .result=COMPILE_PASS
import java.util.List;
import java.util.function.Function;

public class Test {
  void m(List<String> list) {
    list.stream().map(String::length);
  }
}
