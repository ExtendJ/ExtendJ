// Test safe generic type conversion.
// Should not generate unchecked conversion warning.
// See issue 190.
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
