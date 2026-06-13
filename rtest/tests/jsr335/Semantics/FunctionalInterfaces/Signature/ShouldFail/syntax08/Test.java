// .result=COMPILE_FAIL
class Test {
  interface X<A> { <S> void execute(S s, A a); }

  @FunctionalInterface
  interface Exec<A> extends X {
    <S> void execute(S s, A a);
  }
}
