// Using type bounds in constructor for diamond expression.
// https://bitbucket.org/extendj/extendj/issues/307/incorrect-warning-when-using-diamond
// .result: COMPILE_PASS
public class Test {
  Box<G> bl = new Box<>(new G());
}

class Box<U extends E<U>> {
  Box(U u) { }
}

class E<T> { }

class G extends E<G> { }
