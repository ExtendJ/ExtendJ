// Using type bounds in constructor for diamond expression.
// https://bitbucket.org/extendj/extendj/issues/307/incorrect-warning-when-using-diamond
// .result: COMPILE_PASS
public class Test {
  Box<L> bl = new Box<>(new L());
}

class Box<T> {
  <U extends C<U>> Box(U u) { }
}

class C<T> { }

class L extends C<L> { }
