// Using type bounds in constructor for diamond expression.
// See issue 307.
// .result: COMPILE_PASS
public class Test {
  Box<G> bl = new Box<>(new G());
}

class Box<U extends E<U>> {
  Box(U u) { }
}

class E<T> { }

class G extends E<G> { }
