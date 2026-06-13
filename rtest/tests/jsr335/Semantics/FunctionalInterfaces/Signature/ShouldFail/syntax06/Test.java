// .result=COMPILE_FAIL
class Test {
  interface X<A, B> { <S> void execute(S s, A a, B b); }
  interface Y<A, B> { <T> void execute(T t, B b, A a); }

  @FunctionalInterface
  interface Exec<A, B> extends X<A, A>, Y<A, B> { }
}
