// Test type inference for method invocation.
// .result=COMPILE_PASS
interface Container<R> { }

interface Node {
  <R> R unpack(Container<R> v);
}

public class Test<T> implements Container<T> {
  public T visit(Node n) {
    return n.unpack(this);
  }
}
