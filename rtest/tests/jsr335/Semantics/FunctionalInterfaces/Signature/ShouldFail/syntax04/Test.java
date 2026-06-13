// .result=COMPILE_FAIL
class Test {
  interface X { <S> int execute(S[][][][] a); }
  interface Y { <T> int execute(T[][][]   a); }

  @FunctionalInterface
  interface Exec extends Y, X { }
}
