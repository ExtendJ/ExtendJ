// .result=COMPILE_FAIL
class Test {
  interface X { int execute(int a); }
  interface Y { int execute(); }

  @FunctionalInterface
  interface Exec extends Y, X { }
}
