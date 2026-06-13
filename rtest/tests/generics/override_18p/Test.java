// Test that it is possible to override a generic method in a raw super interface.
// .result=COMPILE_PASS
public abstract class Test implements Container {
  @Override
  public Object[] toArray(Object[] array) {
    return array;
  }
}

interface Container<E> {
  <T> T[] toArray(T[] array);
}
