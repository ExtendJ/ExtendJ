// The members of a qualified access are the members of the capture converted
// type of the qualifier, so the parameter of add is the capture variable of
// the wildcard and no argument can be converted to it.
// https://bitbucket.org/extendj/extendj/issues/338/capture-types-becomes-too-general
// .result: COMPILE_FAIL
import java.util.List;

public class Test {
  void m(List<?> l, Object x) {
    l.add(x);
  }
}
