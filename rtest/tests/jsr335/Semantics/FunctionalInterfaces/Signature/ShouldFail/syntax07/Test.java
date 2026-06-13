// .result=COMPILE_FAIL
import java.util.ArrayList;

class Test {

  interface X<A, B> { <S> void execute(S s, A a, B b); }
  interface Y<A, B> { <T> void execute(T t, B b, A a); }

  @FunctionalInterface
  interface Exec<A, B> extends X<ArrayList<B>, A>, Y<A, ArrayList<A>> { }
}
