// Test safe generic type conversion.
// Should not generate unchecked conversion warning.
// https://bitbucket.org/extendj/extendj/issues/190/incorrect-unchecked-conversion-warning
// .result=COMPILE_PASS

abstract class AbstractContainer<T> { }
class List<T> extends AbstractContainer<T> { }
class ResourceList extends List<Resource> { }
interface Resource { }

public class Test {
  List<Resource> foo(AbstractContainer con) {
    // This should not generate an unchecked conversion warning:
    return ((ResourceList) con);
  }
}
