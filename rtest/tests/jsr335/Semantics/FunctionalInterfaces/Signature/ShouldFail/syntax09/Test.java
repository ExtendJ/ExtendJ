// .result=COMPILE_FAIL
class Test {
  @FunctionalInterface
  interface Exec extends X, Y { }
}

interface X { void execute(int a); }
interface Y { void execute(long a); }

