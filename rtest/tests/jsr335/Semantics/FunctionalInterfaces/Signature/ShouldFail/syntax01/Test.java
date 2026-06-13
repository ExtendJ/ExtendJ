// .result=COMPILE_FAIL
import java.util.ArrayList;

class Test {
  interface X { <S, T> int execute(ArrayList<? extends ArrayList<S>> a); }
  interface Y { <T, S> int execute(ArrayList<? extends ArrayList<S>> a); }

  @FunctionalInterface
  interface Exec extends Y, X { }
}
