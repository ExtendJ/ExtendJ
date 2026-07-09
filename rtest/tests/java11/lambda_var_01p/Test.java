// Simple examples using 'var'-declared lambda parameters.
// .result=COMPILE_PASS
public class Test {
  {
    Fun    f1  = (var a) -> { };
    BiFun  f2  = (var a, var b) -> { };
    TriFun f3  = (var a, var b, var c) -> { };
  }
}

interface Fun    { void fun(int a); }
interface BiFun  { void fun(int a, int b); }
interface TriFun { void fun(int a, int b, int c); }
