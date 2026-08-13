// A plain wildcard position of a captured return type cannot be equated with a proper type.
// The ExtendJ error message for this case could be improved.
// .result: COMPILE_FAIL
public abstract class Test {
  abstract <Z> Some<?> some(Z z);

  void test() {
    Some<One> someone = some(1);
  }
}
interface Some<T> { }
interface One { }
