// .result=COMPILE_PASS
import java.util.*;
import java.io.Serializable;

class Test {
  interface X { <A, B, T extends A> void execute(int a); }
  interface Y { <B, A, S extends B> void execute(int a); }

  @FunctionalInterface
  interface Exec extends Y, X { }
}
