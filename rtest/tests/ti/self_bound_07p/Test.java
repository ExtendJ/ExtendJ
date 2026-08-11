// Test that a self-bounded type parameter is instantiated when nothing else bounds it.
// .result: COMPILE_PASS
public class Test {
  interface SneakerNet<E> { E self(); }
  static <T extends SneakerNet<T>> T connect() { return null; }

  {
    connect().self().self();
  }
}

