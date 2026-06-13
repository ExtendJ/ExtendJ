// .result=COMPILE_PASS
public class Test {
  
  interface A {
    default void m() { }
    double m2();
  }

  Integer integ = 5;
  A a = integ::intValue;
}
